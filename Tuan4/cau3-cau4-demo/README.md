# Cau 3 + Cau 4 Full Demo (FE + BE + DB)

Demo nay bao gom:
- FE: dashboard web tai cong 8081
- BE: API Gateway + cac services business
- DB: 3 Postgres (user, order, inventory)
- Event bus: Redis
- 2 mo hinh:
  - Event Choreography
  - Event Orchestration (co 1 orchestrator)

## Kien truc
- Gateway: route request tu FE den cac service.
- User Service + db-user
- Order Service + db-order
- Inventory Service + db-inventory
- Payment Service
- Delivery Service
- Notification Service
- Orchestrator Service
- Redis lam event bus cho choreography

## Chay he thong
1. Mo terminal tai thu muc cau3-cau4-demo
2. Chay:

docker compose up -d --build

3. Mo FE:
- http://localhost:8081

## Luong demo
### 1) Choreography
- Tao user
- Bam Choreography Order
- Event chain:
  - OrderCreated
  - InventoryReserved
  - PaymentSucceeded
  - DeliveryCreated
- Xem ket qua o bang Orders va Events tren FE

### 2) Orchestration
- Bam Orchestration Order
- Orchestrator goi tuan tu:
  - Inventory reserve
  - Payment charge
  - Delivery create
  - update trang thai ORCH_COMPLETED

## API chinh
- GET /api/users
- POST /api/users
- GET /api/inventory
- GET /api/orders
- GET /api/events
- POST /api/choreography/orders
- POST /api/orchestration/orders

## Dung he thong

docker compose down

## Ghi chu test
- Payment policy trong demo: quantity <= 5 thi thanh cong.
- Neu quantity > 5:
  - choreography co the ra PaymentFailed
  - orchestration se fail va compensation release stock.
