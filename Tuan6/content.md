You are a senior software architect and full-stack engineer.

Your task is to generate a COMPLETE Event-Driven Microservices system for a Movie Ticket Booking System.

========================
1. ARCHITECTURE
========================
- Architecture style: Event-Driven Architecture (EDA)
- Communication: ONLY via Message Broker (NO direct REST calls between services)
- Broker: Kafka (preferred) or RabbitMQ
- Each service is independent

Services:
1. API Gateway (Spring Cloud Gateway)
2. User Service (Spring Boot)
3. Movie Service (Spring Boot)
4. Booking Service (Spring Boot) [CORE]
5. Payment Service (Spring Boot)
6. Notification Service (Spring Boot)
7. Frontend (ReactJS)

========================
2. EVENT FLOW (CRITICAL)
========================
Flow must be EXACTLY like this:

Frontend → API Gateway → Booking Service
Booking Service → publish BOOKING_CREATED → Broker

Payment Service:
- consume BOOKING_CREATED
- simulate payment (random success/fail)
- publish:
    - PAYMENT_COMPLETED OR
    - BOOKING_FAILED

Notification Service:
- consume PAYMENT_COMPLETED
- log: "Booking #{id} thành công!"

User Service:
- when register → publish USER_REGISTERED

========================
3. EVENTS DEFINITION
========================
Create event classes (JSON format):

- USER_REGISTERED
- BOOKING_CREATED
- PAYMENT_COMPLETED
- BOOKING_FAILED

Each event must include:
- eventId (UUID)
- timestamp
- payload (object)

========================
4. BACKEND REQUIREMENTS
========================

Use:
- Java Spring Boot
- Spring Kafka (or RabbitMQ)
- REST API
- Lombok
- Maven or Gradle

Each service must include:
- Controller
- Service
- Event Producer
- Event Consumer (if needed)
- Model / DTO

========================
5. SERVICE DETAILS
========================

USER SERVICE:
- POST /register
- POST /login
- On register → publish USER_REGISTERED

MOVIE SERVICE:
- GET /movies
- POST /movies
- simple CRUD, no event needed

BOOKING SERVICE:
- POST /bookings
- GET /bookings
- On create booking:
    - save booking (status = PENDING)
    - publish BOOKING_CREATED
- DO NOT call payment service directly

PAYMENT SERVICE:
- Listen BOOKING_CREATED
- Random result:
    success (70%) / fail (30%)
- Publish:
    PAYMENT_COMPLETED or BOOKING_FAILED

NOTIFICATION SERVICE:
- Listen PAYMENT_COMPLETED
- Print log:
  "User {userId} đã đặt đơn #{bookingId} thành công"

========================
6. FRONTEND (ReactJS)
========================

Pages:
- Login/Register
- Movie List
- Booking Page

Requirements:
- Call ONLY API Gateway
- Use Axios
- Simple UI (no need fancy CSS)

========================
7. API GATEWAY
========================
- Route all services:
    /users → User Service
    /movies → Movie Service
    /bookings → Booking Service

========================
8. INFRA (IMPORTANT)
========================

Provide:
- docker-compose.yml including:
    - kafka
    - zookeeper
    - all services
    - frontend

Each service runs on different port:
- user: 8081
- movie: 8082
- booking: 8083
- payment: 8084
- frontend: 8085

========================
9. BONUS FEATURES
========================
Implement if possible:
- Retry mechanism for payment
- Dead Letter Queue
- Event logging table

========================
10. OUTPUT FORMAT
========================

Generate:
1. Full folder structure
2. Source code for each service
3. Docker setup
4. Sample API calls (Postman)
5. Step-by-step run instructions

IMPORTANT:
- Code must be runnable
- Keep services loosely coupled
- Follow clean architecture
- Add logs for each event flow

========================
GOAL
========================

System must demo this flow:

1. Register user → log USER_REGISTERED
2. Create booking → log BOOKING_CREATED
3. Payment processed → log result
4. Notification prints success message
