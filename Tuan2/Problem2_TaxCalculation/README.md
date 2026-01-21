# Bài 2: Hệ thống Tính thuế Sản phẩm

## Mô tả
Hệ thống tính thuế cho các sản phẩm khác nhau với các loại thuế: VAT, thuế xa xỉ, thuế tiêu thụ đặc biệt, thuế nhập khẩu.

## Áp dụng Design Patterns

### 1. State Pattern
**Mục đích:** Quản lý trạng thái thuế của sản phẩm.

**Các trạng thái:**
- `StandardTaxState`: Sản phẩm thường - không thuế đặc biệt (0%)
- `VATTaxState`: Thuế VAT (10%)
- `LuxuryTaxState`: Thuế sản phẩm xa xỉ (20%)
- `ExciseTaxState`: Thuế tiêu thụ đặc biệt (35%) - thuốc lá, rượu

**Ưu điểm:**
- Sản phẩm có thể chuyển đổi trạng thái thuế
- Mỗi trạng thái có logic tính thuế riêng
- Dễ dàng thêm loại thuế mới

### 2. Strategy Pattern
**Mục đích:** Chọn chiến lược tính thuế khác nhau.

**Các chiến lược:**
- `FlatTaxStrategy`: Thuế cố định (tỷ lệ không đổi)
- `ProgressiveTaxStrategy`: Thuế lũy tiến
  - < 1 triệu: 5%
  - 1-5 triệu: 10%
  - > 5 triệu: 15%
- `ImportTaxStrategy`: Thuế nhập khẩu (25%)

**Ưu điểm:**
- Linh hoạt trong việc áp dụng công thức tính thuế
- Có thể thay đổi chiến lược tại runtime
- Dễ dàng so sánh các phương pháp tính thuế

### 3. Decorator Pattern
**Mục đích:** Thêm các lớp thuế lên giá sản phẩm.

**Các decorator:**
- `VATDecorator`: Thêm VAT 10%
- `LuxuryTaxDecorator`: Thêm thuế xa xỉ 20%
- `ExciseTaxDecorator`: Thêm thuế tiêu thụ đặc biệt 35%
- `ImportTaxDecorator`: Thêm thuế nhập khẩu 25%

**Ưu điểm:**
- Có thể chồng nhiều loại thuế lên nhau
- Tính toán thuế theo từng lớp
- Linh hoạt trong việc kết hợp các loại thuế

## Kết luận

### So sánh 3 Pattern trong bài toán thuế

#### State Pattern
- **Khi nào dùng:** Khi sản phẩm có phân loại rõ ràng (thường, xa xỉ, đặc biệt)
- **Đặc điểm:** Một sản phẩm chỉ ở một trạng thái tại một thời điểm
- **Ví dụ:** Một chiếc xe hoặc là xe thường hoặc là xe xa xỉ

#### Strategy Pattern
- **Khi nào dùng:** Khi có nhiều cách tính thuế khác nhau cho cùng mục đích
- **Đặc điểm:** Chọn một trong nhiều thuật toán
- **Ví dụ:** Chọn giữa thuế cố định, thuế lũy tiến, hoặc thuế nhập khẩu

#### Decorator Pattern
- **Khi nào dùng:** Khi cần áp dụng nhiều loại thuế cùng lúc
- **Đặc điểm:** Chồng nhiều lớp thuế lên nhau
- **Ví dụ:** Sản phẩm nhập khẩu xa xỉ có cả VAT, thuế nhập khẩu và thuế xa xỉ

### Tổng kết cho Bài 2
- **State Pattern** phù hợp khi sản phẩm thuộc một loại thuế cụ thể
- **Strategy Pattern** phù hợp khi cần chọn công thức tính thuế
- **Decorator Pattern** phù hợp khi cần áp dụng nhiều loại thuế đồng thời

**Khuyến nghị:** Trong thực tế, Decorator Pattern là phù hợp nhất cho bài toán thuế vì:
1. Sản phẩm có thể chịu nhiều loại thuế cùng lúc
2. Các loại thuế được tính lần lượt (VAT trước, rồi thuế xa xỉ...)
3. Dễ dàng thêm/bớt các loại thuế

## Cách chạy
```bash
cd Problem2_TaxCalculation
javac *.java
java Main
```
