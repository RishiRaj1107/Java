# Job Portal Microservices System

Spring Boot microservices for a job portal: companies, jobs, reviews, API gateway, and Eureka service registry.

## Services

| Service | Port | Description |
|---------|------|-------------|
| `service-reg` | 8761 | Eureka server |
| `companyMS` | 8081 | Company CRUD + Kafka consumer |
| `jobs` | 8082 | Job CRUD, Feign, Kafka producer, Redis cache |
| `reviewMS` | 8083 | Review CRUD, Redis cache |
| `gateway` | 8084 | Spring Cloud Gateway + JWT |

## Prerequisites

- Java 17+ (project targets 17; newer JDKs usually work)
- Maven 3.9+ **or** use `./mvnw` / `mvnw.cmd` in each service folder
- Docker (optional, for Redis and Kafka)

## Quick start with Docker infrastructure

```bash
docker compose up -d
```

Starts Redis (`6379`) and Kafka (`9092`).

## Run order

1. `service-reg`
2. `companyMS`, `reviewMS`, `jobs` (Eureka + Redis + Kafka for full stack)
3. `gateway`

### Full stack (Redis + Kafka + Eureka)

```bash
cd service-reg && mvnw spring-boot:run
cd companyMS && mvnw spring-boot:run
cd reviewMS && mvnw spring-boot:run
cd jobs && mvnw spring-boot:run
cd gateway && mvnw spring-boot:run
```

### Local dev without Redis/Kafka

Use the `dev` profile:

```bash
mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## API usage (via gateway)

1. Login (any username/password in demo mode):

   `POST http://localhost:8084/auth/login?username=test&password=test`

2. Use the returned token:

   `Authorization: Bearer <token>`

3. Example:

   `GET http://localhost:8084/jobs`

## Build & test

From any service directory:

```bash
mvnw clean test
mvnw clean package
```

Tests use the `dev` profile so Redis/Kafka are not required.

## Architecture notes

- Jobs service calls `companyMS` and `reviewMS` via OpenFeign (Eureka service names).
- Job events are published to Kafka topic `job-topic` and consumed by `companyMS`.
- Gateway routes: `/companies/**`, `/jobs/**`, `/reviews/**` with JWT validation.
