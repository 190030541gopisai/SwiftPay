# SwiftPay

SwiftPay is a modern, microservices-based transaction and ledger processing system designed for high concurrency and secure financial transactions. It utilizes Java 21, Spring Boot, PostgreSQL, Redis, and Apache Kafka to orchestrate payments and maintain an immutable ledger.

## 🏗 System Architecture

The project is structured into distinct microservices, communicating synchronously via HTTP APIs and asynchronously using Kafka for event-driven flows.

### Services

1. **Payment Gateway** (`payment.gateway/`):
   - **Role**: Entry point for payment initiation and orchestrating transaction flow.
   - **Exposed Port**: `8080`
   - **Stack**: Spring Boot, Java 21, PostgreSQL (Metadata/Transaction DB), Redis (Idempotency cache), Kafka (Event publishing).
   - **Key Endpoints**: `POST /v1/payments` to initiate transactions.

2. **Ledger** (`ledger/`):
   - **Role**: Maintains account balances and an immutable history of movements.
   - **Exposed Port**: `8081`
   - **Stack**: Spring Boot, Java 21, PostgreSQL (Ledger entries and Account DB).
   - **Key Endpoints**: Endpoints for checking account existence, getting balances, and fetching historical transactions (`/api/ledger/accounts/...`).

3. **Common Module** (`common/`):
   - **Role**: Shared models, DTOs, enum definitions, and utility classes used by both the Payment Gateway and Ledger services.

### Backing Infrastructure 
*(Configured in `docker-compose.yml`)*

- **PostgreSQL**: Two distinct databases for isolation.
  - `payment_gateway_db` (Port: `5400` on host)
  - `ledger_db` (Port: `5401` on host)
- **Redis Cache**: Used by the Payment Gateway for idempotent request handling and caching (Port: `6379`).
- **Apache Kafka**: Used for decoupled transaction processing and ledger movements (Local port `9092`, container port `29092`).

---

## 🛠 Prerequisites

- **Java 21**
- **Maven**
- **Docker & Docker Compose**
- **JMeter** (Ideally 5.6.3+)
- **tcpdump** (For PCAP generation)

---

## 🚀 Getting Started

### 1. Build the Applications

Since both the Gateway and Ledger services depend on the `common` library, build the applications from the root or build `common` first:

```bash
# Build the common library
cd common && ./mvnw clean install

# Build Ledger
cd ../ledger && ./mvnw clean package -DskipTests

# Build Payment Gateway
cd ../payment.gateway && ./mvnw clean package -DskipTests
```

### 2. Start the Docker Infrastructure

Return to the root directory and start all databases, message brokers, and the microservices:

```bash
docker compose up -d
```

Verify everything is running:

```bash
docker ps
```

---

## 🧪 Testing the APIs

A Postman collection is included in the project under the `postman/` directory:
* **File:** `postman/SwiftPay.postman_collection.json`
* Import this file into Postman to issue pre-configured `POST /v1/payments` requests and test the Ledger GET requests. 

You can also test using CURL:
```bash
curl -X POST http://localhost:8080/v1/payments \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: $(uuidgen)" \
  -d '{
    "senderId": "11111111-1111-1111-1111-111111111111",
    "receiverId": "22222222-2222-2222-2222-222222222222",
    "amount": 1,
    "currency": "INR"
}'
```

---

## Load Testing & Capturing PCAP

To generate network-level evidence for the assignment (250 TPS / 1 million transactions), follow the steps below to capture a PCAP file containing the actual network communication.

### Option 1 — Capture Internal Docker Traffic (Recommended)

Run as Root inside the `payment_gateway` container:
```bash
docker exec -u 0 -it payment_gateway sh
```

Install `tcpdump` inside the container:
```bash
# If Debian/Ubuntu:
apt update && apt install -y tcpdump

# If Alpine:
apk add --no-cache tcpdump
```

Run the capture on the required ports (Ledger API, Kafka, Redis, Postgres):
```bash
tcpdump -i any -nn 'port 8081 or port 29092 or port 6379 or port 5432' -w /tmp/internal.pcap
```

1. Run the JMeter load test in a separate terminal:
   ```bash
   jmeter -n -t load/swiftpay.jmx -l load/result.jtl
   ```
2. Wait for it to complete.
3. Stop the capture with `CTRL + C`.

Copy the PCAP file to your host machine:
```bash
docker cp payment_gateway:/tmp/internal.pcap ./load/internal.pcap
```

### Option 2 — Capture Localhost API Traffic (Easier Alternative)

If capturing internal traffic is not strictly required, capturing the API ingress over loopback is usually sufficient to prove throughput.

Run on the host machine:
```bash
sudo tcpdump -i lo port 8080 -w load/loadtest.pcap
```

Then run JMeter:
```bash
jmeter -n -t load/swiftpay.jmx -l load/result.jtl
```
This produces a valid PCAP showing actual network packets, timestamps, TCP flows, and request throughput.