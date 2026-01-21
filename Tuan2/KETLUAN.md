# Tuần 3 - Design Patterns: State, Strategy, Decorator

## Tổng quan

Đây là bài tập áp dụng 3 Design Patterns: **State**, **Strategy**, và **Decorator** vào 3 bài toán thực tế.

## Cấu trúc Project

```
Tuan3_DesignPatterns/
│
├── Problem1_OrderManagement/      # Bài 1: Hệ thống quản lý đơn hàng
│   ├── Order.java
│   ├── OrderState.java (interface)
│   ├── NewOrderState.java
│   ├── ProcessingState.java
│   ├── DeliveredState.java
│   ├── CancelledState.java
│   ├── ShippingStrategy.java (interface)
│   ├── StandardShipping.java
│   ├── ExpressShipping.java
│   ├── FreeShipping.java
│   ├── OrderComponent.java (interface)
│   ├── BasicOrder.java
│   ├── OrderDecorator.java
│   ├── GiftWrapDecorator.java
│   ├── InsuranceDecorator.java
│   ├── PriorityProcessingDecorator.java
│   ├── Main.java
│   └── README.md
│
├── Problem2_TaxCalculation/       # Bài 2: Hệ thống tính thuế
│   ├── Product.java
│   ├── ProductTaxState.java (interface)
│   ├── StandardTaxState.java
│   ├── VATTaxState.java
│   ├── LuxuryTaxState.java
│   ├── ExciseTaxState.java
│   ├── TaxCalculationStrategy.java (interface)
│   ├── FlatTaxStrategy.java
│   ├── ProgressiveTaxStrategy.java
│   ├── ImportTaxStrategy.java
│   ├── TaxableProduct.java (interface)
│   ├── SimpleProduct.java
│   ├── TaxDecorator.java
│   ├── VATDecorator.java
│   ├── LuxuryTaxDecorator.java
│   ├── ExciseTaxDecorator.java
│   ├── ImportTaxDecorator.java
│   ├── Main.java
│   └── README.md
│
├── Problem3_PaymentSystem/        # Bài 3: Hệ thống thanh toán
│   ├── Payment.java
│   ├── PaymentState.java (interface)
│   ├── PendingPaymentState.java
│   ├── ProcessingPaymentState.java
│   ├── CompletedPaymentState.java
│   ├── FailedPaymentState.java
│   ├── RefundedPaymentState.java
│   ├── PaymentMethodStrategy.java (interface)
│   ├── CreditCardStrategy.java
│   ├── PayPalStrategy.java
│   ├── BankTransferStrategy.java
│   ├── PaymentComponent.java (interface)
│   ├── BasicPayment.java
│   ├── PaymentDecorator.java
│   ├── ProcessingFeeDecorator.java
│   ├── DiscountCodeDecorator.java
│   ├── TransactionFeeDecorator.java
│   ├── LoyaltyPointsDecorator.java
│   ├── Main.java
│   └── README.md
│
└── README.md (file này)
```

## Cách chạy từng bài

### Bài 1: Hệ thống Quản lý Đơn hàng
```bash
cd Problem1_OrderManagement
javac *.java
java Main
```

### Bài 2: Hệ thống Tính thuế
```bash
cd Problem2_TaxCalculation
javac *.java
java Main
```

### Bài 3: Hệ thống Thanh toán
```bash
cd Problem3_PaymentSystem
javac *.java
java Main
```

## Design Patterns được sử dụng

### 1. State Pattern (Behavioral)

**Định nghĩa:** Cho phép đối tượng thay đổi hành vi khi trạng thái nội bộ thay đổi.

**Khi nào dùng:**
- Đối tượng có nhiều trạng thái rõ ràng
- Hành vi thay đổi tùy thuộc vào trạng thái
- Muốn tránh if-else/switch-case phức tạp

**Cấu trúc:**
```
Context (Order, Product, Payment)
    ↓
State Interface (OrderState, ProductTaxState, PaymentState)
    ↓
ConcreteState (NewOrderState, VATTaxState, PendingPaymentState...)
```

**Ưu điểm:**
- ✅ Tách biệt logic của từng trạng thái
- ✅ Dễ dàng thêm trạng thái mới
- ✅ Tuân theo Single Responsibility Principle
- ✅ Tuân theo Open/Closed Principle

**Nhược điểm:**
- ❌ Tăng số lượng class
- ❌ Phức tạp nếu chỉ có ít trạng thái

### 2. Strategy Pattern (Behavioral)

**Định nghĩa:** Định nghĩa một họ các thuật toán, đóng gói từng thuật toán và làm cho chúng có thể thay thế lẫn nhau.

**Khi nào dùng:**
- Có nhiều cách thực hiện một hành vi
- Muốn chọn thuật toán tại runtime
- Cần tách biệt các thuật toán

**Cấu trúc:**
```
Strategy Interface (ShippingStrategy, TaxCalculationStrategy, PaymentMethodStrategy)
    ↓
ConcreteStrategy (StandardShipping, FlatTaxStrategy, CreditCardStrategy...)
    ↑
Context sử dụng
```

**Ưu điểm:**
- ✅ Dễ dàng thay đổi thuật toán tại runtime
- ✅ Tránh inheritance phức tạp
- ✅ Tuân theo Open/Closed Principle
- ✅ Dễ test từng strategy độc lập

**Nhược điểm:**
- ❌ Client phải biết sự khác biệt giữa các strategy
- ❌ Tăng số lượng object

### 3. Decorator Pattern (Structural)

**Định nghĩa:** Cho phép thêm chức năng mới vào đối tượng mà không thay đổi cấu trúc của nó.

**Khi nào dùng:**
- Muốn thêm tính năng động
- Không muốn sửa code gốc
- Cần kết hợp nhiều tính năng linh hoạt
- Inheritance không khả thi (tổ hợp tính năng quá nhiều)

**Cấu trúc:**
```
Component Interface (OrderComponent, TaxableProduct, PaymentComponent)
    ↓
ConcreteComponent (BasicOrder, SimpleProduct, BasicPayment)
    ↓
Decorator (OrderDecorator, TaxDecorator, PaymentDecorator)
    ↓
ConcreteDecorator (GiftWrapDecorator, VATDecorator, DiscountCodeDecorator...)
```

**Ưu điểm:**
- ✅ Thêm tính năng mà không sửa code gốc
- ✅ Kết hợp nhiều decorator linh hoạt
- ✅ Tuân theo Single Responsibility Principle
- ✅ Tuân theo Open/Closed Principle

**Nhược điểm:**
- ❌ Code phức tạp với nhiều lớp decorator
- ❌ Khó debug khi có nhiều lớp wrapper
- ❌ Thứ tự decorator quan trọng

## KẾT LUẬN TỔNG HỢP

### 1. So sánh 3 Pattern

| Tiêu chí | State | Strategy | Decorator |
|----------|-------|----------|-----------|
| **Loại** | Behavioral | Behavioral | Structural |
| **Mục đích** | Quản lý trạng thái | Chọn thuật toán | Thêm tính năng |
| **Số lượng** | Chỉ 1 state tại 1 thời điểm | Chỉ 1 strategy tại 1 thời điểm | Có thể nhiều decorator |
| **Thay đổi** | State tự động chuyển | Client chọn strategy | Client chọn decorator |
| **Độ phức tạp** | Trung bình | Đơn giản | Cao (với nhiều lớp) |

### 2. Khi nào dùng từng Pattern?

#### State Pattern
**Dùng khi:**
- ✅ Đối tượng có vòng đời rõ ràng
- ✅ Hành vi phụ thuộc hoàn toàn vào trạng thái
- ✅ Có quy tắc chuyển trạng thái

**Ví dụ thực tế:**
- Trạng thái đơn hàng: Mới → Xử lý → Giao → Hoàn thành
- Trạng thái kết nối: Pending → Connected → Disconnected
- Trạng thái tài liệu: Draft → Review → Published

#### Strategy Pattern
**Dùng khi:**
- ✅ Có nhiều cách làm cùng một việc
- ✅ Cần chọn cách làm tại runtime
- ✅ Muốn tránh conditional statements

**Ví dụ thực tế:**
- Phương thức thanh toán: Card, PayPal, Banking
- Thuật toán sắp xếp: QuickSort, MergeSort, BubbleSort
- Chiến lược giá: Regular, Discount, Premium

#### Decorator Pattern
**Dùng khi:**
- ✅ Cần thêm tính năng động
- ✅ Muốn kết hợp nhiều tính năng
- ✅ Không muốn sửa code gốc

**Ví dụ thực tế:**
- Thêm topping cho đồ uống: Milk + Sugar + Ice
- Thêm tính năng cho text: Bold + Italic + Underline
- Thêm layer cho request: Auth + Logging + Caching

### 3. Lợi ích khi kết hợp 3 Pattern

**Trong Bài 1 (Đơn hàng):**
- State: Quản lý vòng đời đơn hàng
- Strategy: Chọn phương thức giao hàng
- Decorator: Thêm dịch vụ (gói quà, bảo hiểm...)
- **Kết quả:** Hệ thống linh hoạt, dễ mở rộng

**Trong Bài 2 (Thuế):**
- State: Phân loại sản phẩm (thường, xa xỉ, đặc biệt)
- Strategy: Chọn công thức tính thuế
- Decorator: Chồng nhiều loại thuế
- **Kết quả:** Tính thuế chính xác và linh hoạt

**Trong Bài 3 (Thanh toán):**
- State: Quản lý trạng thái giao dịch
- Strategy: Chọn cổng thanh toán
- Decorator: Thêm phí, giảm giá, điểm
- **Kết quả:** Thanh toán an toàn và tiện lợi

### 4. SOLID Principles được áp dụng

✅ **Single Responsibility Principle (SRP)**
- Mỗi class chỉ làm một việc
- State class chỉ quản lý logic của state đó
- Strategy class chỉ triển khai thuật toán của nó

✅ **Open/Closed Principle (OCP)**
- Mở cho mở rộng (thêm state, strategy, decorator mới)
- Đóng cho sửa đổi (không cần sửa code cũ)

✅ **Liskov Substitution Principle (LSP)**
- Các concrete class có thể thay thế interface của nó
- Client không cần biết đang dùng implementation nào

✅ **Interface Segregation Principle (ISP)**
- Interface nhỏ và tập trung
- Không ép client implement method không dùng

✅ **Dependency Inversion Principle (DIP)**
- Phụ thuộc vào abstraction (interface)
- Không phụ thuộc vào concrete class

### 5. Best Practices

**1. Đặt tên rõ ràng:**
- State: `NewOrderState`, `ProcessingState`
- Strategy: `CreditCardStrategy`, `PayPalStrategy`
- Decorator: `GiftWrapDecorator`, `InsuranceDecorator`

**2. Document rõ ràng:**
- Giải thích từng state/strategy/decorator làm gì
- Ghi chú điều kiện chuyển state
- Mô tả tác dụng của decorator

**3. Test kỹ lưỡng:**
- Test từng state độc lập
- Test chuyển đổi giữa các state
- Test kết hợp nhiều decorator

**4. Handle edge cases:**
- State không hợp lệ
- Strategy null
- Decorator stack quá sâu

### 6. Kết luận cuối cùng

**State, Strategy, và Decorator là 3 pattern mạnh mẽ:**

1. **State Pattern** giúp quản lý trạng thái rõ ràng, tránh if-else phức tạp
2. **Strategy Pattern** giúp chọn thuật toán linh hoạt, dễ mở rộng
3. **Decorator Pattern** giúp thêm tính năng động mà không sửa code gốc

**Khi kết hợp cả 3:**
- Hệ thống trở nên **linh hoạt** và **dễ mở rộng**
- Code **clean**, **dễ đọc** và **dễ maintain**
- Tuân theo đầy đủ **SOLID principles**
- Phù hợp cho **dự án lớn** và **lâu dài**

**Khuyến nghị:**
- Học và hiểu rõ từng pattern trước
- Áp dụng khi thực sự cần thiết
- Không over-engineering (quá phức tạp hóa)
- Luôn cân nhắc giữa tính linh hoạt và độ phức tạp

---

## Tài liệu tham khảo

- Design Patterns: Elements of Reusable Object-Oriented Software (Gang of Four)
- Head First Design Patterns
- Refactoring Guru: https://refactoring.guru/design-patterns

## Tác giả

**Sinh viên:** Nguyen Ba Son  
**MSSV:** 22668921  
**Tuần:** 3 - Design Patterns

---

**Chúc các bạn học tốt!** 🚀
