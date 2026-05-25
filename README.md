# SwiftPay - Real-Time Payment Ledger

**Hackathon Challenge:** A resilient, scalable system that handles peer-to-peer (P2P) money transfers, ensuring data consistency, handling high-volume transactions via caching, and providing real-time audit logs for financial reporting.

## 🛠 Technical Stack

- **Language:** Java 21 (Spring Boot)
- **Databases:** PostgreSQL (Separate DBs for Metadata/Transactions and Ledger entries)
- **Messaging:** Apache Kafka (Event-driven asynchronous updates)
- **Caching:** Redis (Idempotency and balance caching)
- **Documentation:** Swagger/OpenAPI
- **Infrastructure:** Docker & Docker Compose
- **CI/CD:** GitHub Actions (Compiles code, runs tests, builds Docker images)
- **Performance Testing:** JMeter & tcpdump (Packet capture)

---

## 🏗 System Architecture & functional Flow

The system is broken down into modular microservices communicating synchronously via HTTP and asynchronously via Kafka.

### 1. Payment Gateway (Service A)
- **Location:** `payment.gateway/`
- **Role:** REST API entry point for payment initiation.
- **Endpoint:** `POST /v1/payments` (Accepts `sender_id`, `receiver_id`, `amount`, `currency`)
- **Idempotency:** Utilizes **Redis** to ensure the same transaction payload/idempotency key isn't processed twice within a 24-hour window.
- **Workflow:** Saves initial requests to **PostgreSQL** with a `PENDING` status and emits a `PaymentInitiated` event to **Kafka**.
- **Port:** `8080`

### 2. Ledger Service (Service B)
- **Location:** `ledger/`
- **Role:** Consumer and processor for ledger movements and account balances.
- **Workflow:** Consumes `PaymentInitiated` events from Kafka. Performs atomic Debit/Credit balance transfers within a strict DB transaction. Emits `PaymentCompleted` or `PaymentFailed` events back to Kafka.
- **Reporting Endpoint:** Exposes `GET /api/ledger/transactions/{userId}` to fetch the real-time transaction history.
- **Port:** `8081`

### 3. Common Library
- **Location:** `common/`
- **Role:** Code reuse layer containing shared DTOs, Event Models, and utilities leveraged by both the Gateway and Ledger services.

---

## 📋 Non-Functional Implementation

- **API Documentation:** Uses Swagger/OpenAPI for standard documentation.
- **Resilience:** Kafka consumers implement retry mechanisms to safely delay and retry processing if the database is temporarily down.
- **Observability:** Health check endpoints (`/health`) are implemented alongside structured application logging.
- **Error Handling:** Gracefully handles "insufficient funds" logic in the ledger, manages database constraint violations, and guards against Kafka outages.

---

## 🚀 Getting Started

### 1. Build the Applications

Since both the Gateway and Ledger services depend on the `common` library, build the applications from the root:

```bash
# Build the common library
cd common && ./mvnw clean install

# Build Ledger
cd ../ledger && ./mvnw clean package -DskipTests

# Build Payment Gateway
cd ../payment.gateway && ./mvnw clean package -DskipTests
```

### 2. Spin up the Ecosystem

The `docker-compose.yml` provides the entire containerized ecosystem (Spring Boot Apps + Postgres + Kafka + Redis).

```bash
cd ..
docker compose up -d
```

Verify everything is running successfully (`docker ps`). Both Postgres DBs, Redis, Kafka, the Ledger App, and the Payment Gateway App should be `Up`.

---

## 🧪 Testing the APIs

A Postman collection is included in the project under the `postman/` directory:
* **File:** `postman/SwiftPay.postman_collection.json`
* Import this file into Postman to issue pre-configured `POST /v1/payments` requests and test the Ledger GET requests. 

**CURL Example (Payment Gateway):**
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

## ⚡ Load Testing & Capturing PCAP

As per the hackathon submission criteria, a load test (250 TPS totaling 1 million transactions) must be executed and evidenced via a PCAP file containing actual network/TCP traffic between the instances.

### Generating the Evidence

**Option 1 — Capture Internal Docker Traffic (Recommended/Containerized)**
1. Enter the gateway container as root: `docker exec -u 0 -it payment_gateway sh`
2. Install tcpdump: `apt update && apt install -y tcpdump`
3. Capture traffic: `tcpdump -i any -nn 'port 8081 or port 29092 or port 6379 or port 5432' -w /tmp/internal.pcap`
4. Run JMeter from host: `jmeter -n -t load/swiftpay.jmx -l load/result.jtl`
5. Stop dump (`CRTL+C`) and copy to host: `docker cp payment_gateway:/tmp/internal.pcap ./load/internal.pcap`

**Option 2 — Capture Localhost API Ingress (Alternative)**
1. Run dump on the host pointing to the entry port: `sudo tcpdump -i lo port 8080 -w load/loadtest.pcap`
2. Execute JMeter at 250 TPS config: `jmeter -n -t load/swiftpay.jmx -l load/result.jtl`
3. Hit `CTRL+C` when the test finishes. Push the resulting `.pcap` to GitHub.
