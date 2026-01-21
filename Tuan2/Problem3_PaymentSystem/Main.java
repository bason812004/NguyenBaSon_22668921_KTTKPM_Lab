public class Main {
    public static void main(String[] args) {
        System.out.println("=== HỆ THỐNG THANH TOÁN ===\n");

        // ========== STATE PATTERN ==========
        System.out.println("--- STATE PATTERN: Trạng thái thanh toán ---");
        
        Payment payment1 = new Payment("PAY001", 500000);
        System.out.println("\n1. Tạo thanh toán mới #" + payment1.getPaymentId());
        System.out.println("   Trạng thái: " + payment1.getState().getStateName());
        
        System.out.println("\n2. Xử lý thanh toán:");
        payment1.process(); // Pending -> Processing
        
        System.out.println("\n3. Hoàn thành thanh toán:");
        payment1.complete(); // Processing -> Completed
        
        System.out.println("\n4. Thử hoàn tiền:");
        payment1.refund(); // Completed -> Refunded

        // Failed payment scenario
        System.out.println("\n5. Kịch bản thanh toán thất bại:");
        Payment payment2 = new Payment("PAY002", 300000);
        payment2.process(); // Pending -> Processing
        payment2.fail(); // Processing -> Failed
        payment2.process(); // Try to process failed payment

        // ========== STRATEGY PATTERN ==========
        System.out.println("\n\n--- STRATEGY PATTERN: Phương thức thanh toán ---");
        
        double amount = 1200000;
        
        PaymentMethodStrategy creditCard = new CreditCardStrategy("1234567890123456", "Nguyen Van A");
        System.out.println("\n1. " + creditCard.getDescription());
        System.out.println("   ---");
        creditCard.processPayment(amount);
        
        PaymentMethodStrategy paypal = new PayPalStrategy("nguyenvana@email.com");
        System.out.println("\n2. " + paypal.getDescription());
        System.out.println("   ---");
        paypal.processPayment(amount);
        
        PaymentMethodStrategy bankTransfer = new BankTransferStrategy("0123456789", "Vietcombank");
        System.out.println("\n3. " + bankTransfer.getDescription());
        System.out.println("   ---");
        bankTransfer.processPayment(amount);

        // ========== DECORATOR PATTERN ==========
        System.out.println("\n\n--- DECORATOR PATTERN: Tùy chỉnh thanh toán ---");
        
        PaymentComponent basicPayment = new BasicPayment(1000000, "Thanh toán đơn hàng");
        System.out.println("\n1. " + basicPayment.getDescription());
        System.out.println("   Số tiền: " + basicPayment.getAmount() + " VND");

        PaymentComponent paymentWithFee = new ProcessingFeeDecorator(basicPayment, 0.03);
        System.out.println("\n2. " + paymentWithFee.getDescription());
        System.out.println("   Số tiền: " + paymentWithFee.getAmount() + " VND");

        PaymentComponent paymentWithDiscount = new DiscountCodeDecorator(
            basicPayment, 
            "SUMMER2026", 
            0.15
        );
        System.out.println("\n3. " + paymentWithDiscount.getDescription());
        System.out.println("   Số tiền: " + paymentWithDiscount.getAmount() + " VND");

        PaymentComponent fullyDecoratedPayment = new TransactionFeeDecorator(
            new ProcessingFeeDecorator(
                new LoyaltyPointsDecorator(
                    new DiscountCodeDecorator(
                        new BasicPayment(2000000, "Thanh toán VIP"),
                        "VIP10",
                        0.10
                    ),
                    50000
                ),
                0.02
            ),
            5000
        );
        System.out.println("\n4. " + fullyDecoratedPayment.getDescription());
        System.out.println("   Số tiền ban đầu: 2,000,000 VND");
        System.out.println("   Số tiền cuối cùng: " + fullyDecoratedPayment.getAmount() + " VND");

        // ========== COMBINATION EXAMPLE ==========
        System.out.println("\n\n--- KẾT HỢP CÁC PATTERN ---");
        System.out.println("\nXử lý thanh toán hoàn chỉnh với tất cả các tính năng:");
        
        // Create payment with decorators
        PaymentComponent decoratedPayment = new TransactionFeeDecorator(
            new ProcessingFeeDecorator(
                new DiscountCodeDecorator(
                    new BasicPayment(3000000, "Đơn hàng điện thoại"),
                    "TECH20",
                    0.20
                ),
                0.03
            ),
            10000
        );
        
        System.out.println("\nChi tiết thanh toán:");
        System.out.println(decoratedPayment.getDescription());
        System.out.println("Số tiền gốc: 3,000,000 VND");
        System.out.println("Số tiền thanh toán: " + decoratedPayment.getAmount() + " VND");
        
        // Create payment with state
        Payment finalPayment = new Payment("PAY003", decoratedPayment.getAmount());
        
        // Choose payment strategy
        PaymentMethodStrategy chosenMethod = new CreditCardStrategy("9876543210123456", "Tran Thi B");
        System.out.println("\nPhương thức: " + chosenMethod.getMethodName());
        
        // Process payment through states
        System.out.println("\nQuy trình thanh toán:");
        System.out.println("Trạng thái hiện tại: " + finalPayment.getState().getStateName());
        
        finalPayment.process(); // Pending -> Processing
        System.out.println("Trạng thái: " + finalPayment.getState().getStateName());
        
        boolean success = chosenMethod.processPayment(finalPayment.getAmount());
        if (success) {
            finalPayment.complete(); // Processing -> Completed
            System.out.println("Trạng thái: " + finalPayment.getState().getStateName());
        }
        
        System.out.println("\n✓ Thanh toán hoàn tất thành công!");

        System.out.println("\n=== KẾT THÚC ===");
    }
}
