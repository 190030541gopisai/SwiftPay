# syntax=docker/dockerfile:1.7

FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /workspace

COPY common common
COPY ledger ledger

RUN mvn -q -f common/pom.xml -DskipTests install
RUN mvn -q -f ledger/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd --system --uid 1001 spring

COPY --from=builder /workspace/ledger/target/*.jar /app/app.jar

USER spring

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

