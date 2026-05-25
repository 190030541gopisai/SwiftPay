# SwiftPay

SwiftPay is a real-time payment ledger built for a fintech hackathon scenario. The repo demonstrates a two-service money transfer flow backed by PostgreSQL, Kafka, Redis, Swagger/OpenAPI, Docker Compose, and GitHub Actions.

The focus of the implementation is a consistent payment lifecycle:

1. A client submits a payment to the gateway.
2. The gateway checks idempotency and balance availability, stores the payment as `PENDING`, and publishes a Kafka event.
3. The ledger service consumes the event, performs the debit/credit inside a database transaction, records ledger entries, and emits a completion or failure event.
4. The gateway consumes the status event and updates the payment record.

## What is in the repo

- `payment.gateway/` - REST API for creating payments and tracking final status.
- `ledger/` - Kafka consumer, balance transfer processor, and reporting API.
- `common/` - Shared Kafka event records.
- `load/` - JMeter plan, result output, and a PCAP capture for the load-test submission.
- `postman/` - Postman collection for manual API testing.

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Cloud 2025.1.1
- PostgreSQL
- Apache Kafka
- Redis
- Springdoc OpenAPI / Swagger UI
- Docker Compose
- GitHub Actions

## Architecture

### Payment Gateway

Location: `payment.gateway/`

Responsibilities:

- Exposes `POST /v1/payments`.
- Requires an `Idempotency-Key` header.
- Uses Redis to reject duplicate requests for 24 hours.
- Checks the sender balance and receiver existence through the ledger service.
- Persists the payment as `PENDING` in PostgreSQL.
- Publishes `PaymentInitiatedEvent` to Kafka.
- Consumes `PaymentCompletedEvent` and `PaymentFailedEvent` to update the payment status.

Runtime port: `8080`

### Ledger Service

Location: `ledger/`

Responsibilities:

- Consumes `PaymentInitiatedEvent` from Kafka.
- Locks the sender and receiver rows and performs the transfer in a single database transaction.
- Writes debit and credit ledger entries.
- Publishes `PaymentCompletedEvent` or `PaymentFailedEvent` back to Kafka.
- Exposes reporting endpoints for balances, account existence, and transaction history.

Runtime port: `8081`

### Shared Events

The `common/` module contains the Kafka payloads used by both services:

- `PaymentInitiatedEvent`
- `PaymentCompletedEvent`
- `PaymentFailedEvent`

## Kafka Topics

- `payment-initiated`
- `payment-completed`
- `payment-failed`

## API Endpoints

### Payment Gateway

- `POST /v1/payments`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health: `http://localhost:8080/actuator/health`

Example request:

```bash
curl -X POST http://localhost:8080/v1/payments \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: txn-123" \
  -d '{
    "senderId": "11111111-1111-1111-1111-111111111111",
    "receiverId": "22222222-2222-2222-2222-222222222222",
    "amount": 10.00,
    "currency": "USD"
  }'
```

### Ledger Service

- `GET /api/ledger/accounts/{accountId}/exists`
- `GET /api/ledger/accounts/{accountId}/balance`
- `GET /api/ledger/transactions/{userId}`
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- Health: `http://localhost:8081/actuator/health`

### Common Error Cases

- `400` - Missing or invalid request data, including a missing idempotency key.
- `404` - Sender or receiver account not found.
- `409` - Duplicate transaction detected by Redis idempotency.
- `422` - Insufficient balance.
- `503` - Ledger service unavailable.

## Local Development

The easiest way to run the full stack locally is Docker Compose.

### Prerequisites

- Java 21
- Docker and Docker Compose
- Maven wrapper support is already committed in each module

### Start the stack

```bash
docker compose up --build
```

This starts:

- PostgreSQL for the payment gateway on `localhost:5400`
- PostgreSQL for the ledger service on `localhost:5401`
- Redis on `localhost:6379`
- Kafka on `localhost:9092`
- Payment Gateway on `localhost:8080`
- Ledger on `localhost:8081`

### Run services without Docker

If you want to run the services from the command line, start the dependencies first and then build the shared module before the services:

```bash
cd common
./mvnw -B clean install

cd ../ledger
./mvnw -B clean package

cd ../payment.gateway
./mvnw -B clean package
```

## Seed Data

The ledger service seeds a few local accounts in `ledger/src/main/resources/data.sql` so the flow can be tested immediately.

Useful demo accounts:

- `11111111-1111-1111-1111-111111111111`
- `22222222-2222-2222-2222-222222222222`
- `33333333-3333-3333-3333-333333333333`
- `44444444-4444-4444-4444-444444444444`

## Testing

### Unit and integration tests

Each module has its own test suite.

The payment gateway integration test is disabled by default and must be enabled from the module directory with:

```bash
cd payment.gateway
./mvnw -DrunIntegrationTests=true test
```

The GitHub Actions workflow runs the module builds and tests with integration support enabled.

### GitHub Actions

Workflow: `.github/workflows/ci.yml`

The pipeline:

- waits for PostgreSQL, Redis, and Kafka services
- installs the shared `common` module
- builds and tests `ledger`
- builds and tests `payment.gateway`
- builds both Docker images

## Load Test and PCAP

The hackathon brief asks for a 250 TPS load test with a PCAP trace. This repo includes the artifacts used for that workflow in `load/`:

- `swiftpay.jmx` - JMeter plan
- `result.jtl` - JMeter results output
- `internal.pcap` - captured traffic trace

To rerun the capture, execute the JMeter plan while capturing traffic with `tcpdump` against the gateway and downstream ports.

## Notes

- This repo currently ships with Docker Compose for local orchestration.
- Kubernetes manifests and the optional analytics worker are not included in the current codebase.
- The gateway uses OpenFeign plus Resilience4j for downstream communication and retry-friendly behavior.
