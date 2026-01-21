// Concrete State: Completed
public class CompletedPaymentState implements PaymentState {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Thanh toán đã được hoàn thành trước đó.");
    }

    @Override
    public void completePayment(Payment payment) {
        System.out.println("Thanh toán đã hoàn thành.");
    }

    @Override
    public void failPayment(Payment payment) {
        System.out.println("Không thể đánh dấu thất bại cho thanh toán đã hoàn thành!");
    }

    @Override
    public void refundPayment(Payment payment) {
        System.out.println("Đang hoàn tiền cho thanh toán #" + payment.getPaymentId());
        System.out.println("Số tiền hoàn: " + payment.getAmount() + " VND");
        payment.setState(new RefundedPaymentState());
    }

    @Override
    public String getStateName() {
        return "Hoàn thành";
    }
}
