// Concrete State: Refunded
public class RefundedPaymentState implements PaymentState {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Không thể xử lý thanh toán đã được hoàn tiền!");
    }

    @Override
    public void completePayment(Payment payment) {
        System.out.println("Không thể hoàn thành thanh toán đã được hoàn tiền!");
    }

    @Override
    public void failPayment(Payment payment) {
        System.out.println("Không thể đánh dấu thất bại cho thanh toán đã hoàn tiền!");
    }

    @Override
    public void refundPayment(Payment payment) {
        System.out.println("Thanh toán đã được hoàn tiền trước đó.");
    }

    @Override
    public String getStateName() {
        return "Đã hoàn tiền";
    }
}
