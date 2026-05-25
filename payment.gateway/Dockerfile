# syntax=docker/dockerfile:1.7

FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /workspace

COPY common common
COPY payment.gateway payment.gateway

RUN mvn -q -f common/pom.xml -DskipTests install
RUN mvn -q -f payment.gateway/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd --system --uid 1001 spring

COPY --from=builder /workspace/payment.gateway/target/*.jar /app/app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
