# Bài 3: Hệ thống Thanh toán

## Mô tả
Hệ thống thanh toán với các phương thức khác nhau (Thẻ tín dụng, PayPal, Chuyển khoản) và các tính năng bổ sung (Phí xử lý, Mã giảm giá, Điểm tích lũy).

## Áp dụng Design Patterns

### 1. State Pattern
**Mục đích:** Quản lý trạng thái của giao dịch thanh toán.

**Các trạng thái:**
- `PendingPaymentState`: Chờ xử lý
- `ProcessingPaymentState`: Đang xử lý
- `CompletedPaymentState`: Hoàn thành
- `FailedPaymentState`: Thất bại
- `RefundedPaymentState`: Đã hoàn tiền

**Luồng trạng thái:**
```
Pending → Processing → Completed → Refunded
              ↓
           Failed
```

**Ưu điểm:**
- Kiểm soát vòng đời giao dịch
- Ngăn chặn thao tác không hợp lệ
- Dễ dàng theo dõi trạng thái

### 2. Strategy Pattern
**Mục đích:** Chọn phương thức thanh toán linh hoạt.

**Các chiến lược:**
- `CreditCardStrategy`: Thanh toán qua thẻ tín dụng
  - Xác thực số thẻ
  - Kiểm tra hạn mức
- `PayPalStrategy`: Thanh toán qua PayPal
  - Xác thực email
  - Chuyển hướng đến PayPal
- `BankTransferStrategy`: Chuyển khoản ngân hàng
  - Xác nhận tài khoản
  - Chờ xác nhận từ ngân hàng

**Ưu điểm:**
- Dễ dàng thêm phương thức thanh toán mới
- Logic xử lý độc lập cho mỗi phương thức
- Người dùng tự do lựa chọn

### 3. Decorator Pattern
**Mục đích:** Thêm các tính năng bổ sung cho thanh toán.

**Các decorator:**
- `ProcessingFeeDecorator`: Phí xử lý (% trên tổng tiền)
- `DiscountCodeDecorator`: Mã giảm giá
- `TransactionFeeDecorator`: Phí giao dịch cố định
- `LoyaltyPointsDecorator`: Trừ điểm tích lũy

**Ví dụ:**
```
Giá gốc: 2,000,000 VND
- Mã giảm giá 10%: -200,000 VND
- Điểm tích lũy: -50,000 VND
+ Phí xử lý 2%: +35,000 VND
+ Phí giao dịch: +5,000 VND
= Tổng thanh toán: 1,790,000 VND
```

**Ưu điểm:**
- Linh hoạt kết hợp nhiều tính năng
- Tính toán từng bước rõ ràng
- Dễ dàng thêm/bớt tính năng

## Kết luận

### Phân tích vai trò của từng Pattern

#### State Pattern - Quản lý trạng thái
**Vai trò:** Đảm bảo tính toàn vẹn của giao dịch
- Ngăn hoàn tiền cho thanh toán chưa hoàn thành
- Ngăn xử lý lại thanh toán đã thất bại
- Theo dõi vòng đời giao dịch

**Quan trọng:** ⭐⭐⭐⭐⭐ (Rất quan trọng cho bảo mật)

#### Strategy Pattern - Phương thức thanh toán
**Vai trò:** Cung cấp nhiều lựa chọn cho người dùng
- Mỗi phương thức có luồng xử lý riêng
- Dễ dàng tích hợp cổng thanh toán mới
- Linh hoạt về phí và thời gian xử lý

**Quan trọng:** ⭐⭐⭐⭐⭐ (Bắt buộc có)

#### Decorator Pattern - Tính năng bổ sung
**Vai trò:** Tăng trải nghiệm người dùng
- Áp dụng khuyến mãi linh hoạt
- Tính phí minh bạch
- Sử dụng điểm tích lũy

**Quan trọng:** ⭐⭐⭐⭐ (Quan trọng cho marketing)

### So sánh với 2 bài trước

| Pattern | Bài 1 (Đơn hàng) | Bài 2 (Thuế) | Bài 3 (Thanh toán) |
|---------|------------------|--------------|-------------------|
| **State** | Trạng thái đơn | Phân loại sản phẩm | Trạng thái giao dịch |
| **Strategy** | Phương thức ship | Công thức tính thuế | Phương thức thanh toán |
| **Decorator** | Dịch vụ thêm | Các lớp thuế | Phí/giảm giá |

### Tổng kết chung

**Điểm chung của 3 bài:**
1. Cả 3 pattern đều có thể áp dụng cho mỗi bài toán
2. Mỗi pattern giải quyết một khía cạnh khác nhau
3. Kết hợp 3 pattern tạo hệ thống hoàn chỉnh và linh hoạt

**Nguyên tắc chọn pattern:**
- **State Pattern:** Khi đối tượng có vòng đời rõ ràng với các trạng thái
- **Strategy Pattern:** Khi có nhiều cách thực hiện cùng một hành vi
- **Decorator Pattern:** Khi cần thêm tính năng động mà không sửa code gốc

**Lợi ích khi kết hợp:**
- Tách biệt trách nhiệm (Separation of Concerns)
- Dễ bảo trì và mở rộng
- Code clean và dễ đọc
- Tuân theo SOLID principles

## Cách chạy
```bash
cd Problem3_PaymentSystem
javac *.java
java Main
```
