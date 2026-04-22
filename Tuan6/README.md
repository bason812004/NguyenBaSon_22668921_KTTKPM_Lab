# Event-Driven Movie Ticket Booking System

Complete Event-Driven Microservices implementation for Movie Ticket Booking using Spring Boot, Kafka, API Gateway, and React frontend.

## 1) Architecture

- Architecture style: Event-Driven Architecture (EDA)
- Communication between services: Kafka only (no direct REST service-to-service calls)
- Broker: Kafka
- API entry point: API Gateway
- Frontend calls only API Gateway

Services:

- api-gateway (Spring Cloud Gateway) - port 8080
- user-service (Spring Boot) - port 8081
- movie-service (Spring Boot) - port 8082
- booking-service (Spring Boot, core) - port 8083
- payment-service (Spring Boot) - port 8084
- notification-service (Spring Boot) - port 8086
- frontend (React + Vite) - port 8085

## 2) Required Event Flow

Implemented exactly as requested:

1. Frontend -> API Gateway -> Booking Service
2. Booking Service -> publish BOOKING_CREATED -> Kafka
3. Payment Service:
   - consume BOOKING_CREATED
   - simulate payment (success 70% / fail 30%)
   - publish PAYMENT_COMPLETED or BOOKING_FAILED
4. Notification Service:
   - consume PAYMENT_COMPLETED
  - log: User {userId} đã đặt đơn #{bookingId} thành công
5. User Service:
   - when register -> publish USER_REGISTERED

## 3) Event Definitions

Implemented event classes in module `common-events`:

- USER_REGISTERED (`UserRegisteredEvent`)
- BOOKING_CREATED (`BookingCreatedEvent`)
- PAYMENT_COMPLETED (`PaymentCompletedEvent`)
- BOOKING_FAILED (`BookingFailedEvent`)

Each event includes:

- eventId (UUID string)
- timestamp (Instant)
- payload (object)

## 4) Project Structure

```
.
|-- pom.xml
|-- docker-compose.yml
|-- README.md
|-- common-events
|   |-- pom.xml
|   `-- src/main/java/com/movie/events
|-- api-gateway
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
|-- user-service
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
|-- movie-service
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
|-- booking-service
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
|-- payment-service
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
|-- notification-service
|   |-- Dockerfile
|   |-- pom.xml
|   `-- src/main
`-- frontend
    |-- Dockerfile
    |-- package.json
    |-- vite.config.js
    `-- src
```

## 5) API Gateway Routes

- /users/** -> user-service (`/users/register` rewritten to `/register`, `/users/login` to `/login`)
- /movies/** -> movie-service
- /bookings/** -> booking-service

Frontend only calls API Gateway at `http://localhost:8080`.

## 6) Endpoints

### User Service

- POST `/register`
- POST `/login`

Via gateway:

- POST `/users/register`
- POST `/users/login`

### Movie Service

- GET `/movies`
- POST `/movies`
- PUT `/movies/{id}`
- DELETE `/movies/{id}`

### Booking Service

- POST `/bookings`
- GET `/bookings`

## 7) Bonus Features Implemented

- Retry mechanism for payment using Spring Kafka `@RetryableTopic`
- Dead Letter Queue for BOOKING_CREATED failures (`booking.created.dlt`)
- Event logging table in booking-service (`EventLog` entity with H2)

## 8) Run Instructions

### Option A: Run all with Docker Compose (recommended)

From project root:

```bash
docker compose up --build
```

Open:

- Frontend: http://localhost:8085
- API Gateway: http://localhost:8080

### Option B: Run locally without Docker

Requirements:

- Java 17+
- Maven 3.9+
- Node 20+
- Kafka + Zookeeper running locally

Build Java modules:

```bash
mvn clean package -DskipTests
```

Run backend services (in separate terminals):

```bash
mvn -pl user-service spring-boot:run
mvn -pl movie-service spring-boot:run
mvn -pl booking-service spring-boot:run
mvn -pl payment-service spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Run frontend:

```bash
cd frontend
npm install
npm run dev -- --host 0.0.0.0 --port 8085
```

## 9) Sample API Calls

### 1. Register user -> USER_REGISTERED

```bash
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "email": "alice@example.com",
    "password": "123456"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "123456"
  }'
```

### 3. Create movie

```bash
curl -X POST http://localhost:8080/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Dune 2",
    "genre": "Sci-Fi",
    "durationMinutes": 166,
    "description": "Epic sci-fi"
  }'
```

### 4. List movies

```bash
curl http://localhost:8080/movies
```

### 5. Create booking -> BOOKING_CREATED

```bash
curl -X POST http://localhost:8080/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "movieId": 1,
    "seatCount": 2,
    "totalPrice": 200
  }'
```

### 6. Get bookings (status updates after payment event)

```bash
curl http://localhost:8080/bookings
```

## 10) Demo Goal Checklist

- Register user -> USER_REGISTERED logged in user-service
- Create booking -> BOOKING_CREATED logged in booking-service
- Payment processed -> PAYMENT_COMPLETED or BOOKING_FAILED logged in payment-service
- Notification success message printed in notification-service logs

## 11) Frontend Authentication UI/UX

React auth system is implemented with Context API + React Router + Axios.

### Frontend Structure

```text
frontend/src
|-- App.jsx
|-- main.jsx
|-- styles.css
|-- routes/
|   `-- AppRoutes.jsx
|-- components/
|   |-- Navbar.jsx
|   `-- ProtectedRoute.jsx
|-- pages/
|   |-- Login.jsx
|   |-- Register.jsx
|   |-- Movies.jsx
|   `-- Booking.jsx
|-- context/
|   `-- AuthContext.jsx
|-- services/
|   `-- api.js
`-- api/
    `-- client.js
```

### Auth Behavior

- Not logged in:
  - Navbar shows Login and Register
  - Protected routes (`/movies`, `/booking`) redirect to `/login`
- Logged in:
  - Navbar shows `Welcome {username}` and Logout button
  - Access to Movie List and Booking page is allowed
- Persist login:
  - Auth state is stored in localStorage key `movie.auth`
  - Refresh restores user auth state automatically
- Redirect rules:
  - Login success -> `/movies`
  - Logout -> `/login`

### Example API Responses

Register response (`POST /users/register`):

```json
{
  "userId": 1,
  "username": "alice",
  "email": "alice@example.com",
  "message": "Register successful"
}
```

Login response (`POST /users/login`):

```json
{
  "success": true,
  "message": "Login successful",
  "userId": 1,
  "username": "alice",
  "email": "alice@example.com"
}
```

Booking response (`POST /bookings`):

```json
{
  "id": 12,
  "userId": 1,
  "movieId": 1,
  "seatCount": 2,
  "totalPrice": 240,
  "status": "PENDING"
}
```

### Frontend Run Instructions

Use Docker compose (already configured):

```bash
docker compose up -d --build
```

Open:

- Frontend: http://localhost:8085
- API Gateway: http://localhost:8080

Use API test file:

- `sample-api-calls.http`

