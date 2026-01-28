git branch -M main// Concrete State: Processing
public class ProcessingPaymentState implements PaymentState {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Thanh toán đang được xử lý...");
        System.out.println("Xác thực thông tin thanh toán...");
    }

    @Override
    public void completePayment(Payment payment) {
        System.out.println("Thanh toán #" + payment.getPaymentId() + " thành công!");
        System.out.println("Số tiền đã thanh toán: " + payment.getAmount() + " VND");
        payment.setState(new CompletedPaymentState());
    }

    @Override
    public void failPayment(Payment payment) {
        System.out.println("Thanh toán #" + payment.getPaymentId() + " thất bại!");
        System.out.println("Lý do: Lỗi xác thực hoặc không đủ số dư");
        payment.setState(new FailedPaymentState());
    }

    @Override
    public void refundPayment(Payment payment) {
        System.out.println("Không thể hoàn tiền cho thanh toán đang xử lý!");
    }

    @Override
    public String getStateName() {
        return "Đang xử lý";
    }
}
