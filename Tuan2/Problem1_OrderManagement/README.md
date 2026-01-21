# Bài 1: Hệ thống Quản lý Đơn hàng

## Mô tả
Hệ thống quản lý đơn hàng với các trạng thái: Mới tạo, Đang xử lý, Đã giao, và Hủy.

## Áp dụng Design Patterns

### 1. State Pattern
**Mục đích:** Quản lý các trạng thái khác nhau của đơn hàng.

**Các lớp:**
- `OrderState` (interface): Định nghĩa hành vi chung
- `NewOrderState`: Trạng thái mới tạo - kiểm tra thông tin
- `ProcessingState`: Đang xử lý - đóng gói và vận chuyển
- `DeliveredState`: Đã giao - cập nhật trạng thái
- `CancelledState`: Đã hủy - hoàn tiền

**Ưu điểm:**
- Tách biệt logic của từng trạng thái
- Dễ dàng thêm trạng thái mới
- Tránh sử dụng if-else phức tạp

### 2. Strategy Pattern
**Mục đích:** Chọn phương thức vận chuyển khác nhau.

**Các chiến lược:**
- `StandardShipping`: Giao hàng tiêu chuẩn (3-5 ngày) - 30,000 VND
- `ExpressShipping`: Giao hàng nhanh (1-2 ngày) - 60,000 VND
- `FreeShipping`: Miễn phí cho đơn >= 500,000 VND

**Ưu điểm:**
- Dễ dàng thay đổi phương thức vận chuyển
- Có thể thêm phương thức mới mà không ảnh hưởng code cũ
- Tách biệt logic tính phí vận chuyển

### 3. Decorator Pattern
**Mục đích:** Thêm các tính năng bổ sung cho đơn hàng.

**Các decorator:**
- `GiftWrapDecorator`: Thêm dịch vụ gói quà (+15,000 VND)
- `InsuranceDecorator`: Bảo hiểm vận chuyển (+2% giá trị)
- `PriorityProcessingDecorator`: Xử lý ưu tiên (+25,000 VND)

**Ưu điểm:**
- Thêm tính năng động mà không sửa code gốc
- Có thể kết hợp nhiều decorator
- Tuân theo Open/Closed Principle

## Kết luận

### Khi nào sử dụng State Pattern?
- Đối tượng có nhiều trạng thái rõ ràng
- Hành vi thay đổi theo trạng thái
- Muốn tránh if-else phức tạp

### Khi nào sử dụng Strategy Pattern?
- Có nhiều cách thực hiện một hành vi
- Muốn chọn thuật toán tại runtime
- Cần tách biệt các thuật toán

### Khi nào sử dụng Decorator Pattern?
- Muốn thêm tính năng động
- Không muốn sửa code gốc
- Cần kết hợp nhiều tính năng linh hoạt

### Tổng kết cho Bài 1
Trong hệ thống quản lý đơn hàng:
- **State Pattern** quản lý vòng đời đơn hàng (Mới → Xử lý → Giao → Hủy)
- **Strategy Pattern** cho phép chọn phương thức vận chuyển phù hợp
- **Decorator Pattern** thêm các dịch vụ bổ sung linh hoạt

Ba pattern này hoạt động độc lập nhưng có thể kết hợp để tạo hệ thống linh hoạt và dễ mở rộng.

## Cách chạy
```bash
cd Problem1_OrderManagement
javac *.java
java Main
```
