// Concrete State: Pending
public class PendingPaymentState implements PaymentState {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Đang xử lý thanh toán #" + payment.getPaymentId());
        System.out.println("Số tiền: " + payment.getAmount() + " VND");
        payment.setState(new ProcessingPaymentState());
    }

    @Override
    public void completePayment(Payment payment) {
        System.out.println("Không thể hoàn thành thanh toán chưa được xử lý!");
    }

    @Override
    public void failPayment(Payment payment) {
        System.out.println("Hủy thanh toán #" + payment.getPaymentId());
        payment.setState(new FailedPaymentState());
    }

    @Override
    public void refundPayment(Payment payment) {
        System.out.println("Không thể hoàn tiền cho thanh toán chưa hoàn thành!");
    }

    @Override
    public String getStateName() {
        return "Chờ xử lý";
    }
}
