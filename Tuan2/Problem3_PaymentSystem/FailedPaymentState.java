// Concrete State: Failed
public class FailedPaymentState implements PaymentState {
    @Override
    public void processPayment(Payment payment) {
        System.out.println("Không thể xử lý thanh toán đã thất bại. Vui lòng tạo thanh toán mới.");
    }

    @Override
    public void completePayment(Payment payment) {
        System.out.println("Không thể hoàn thành thanh toán đã thất bại!");
    }

    @Override
    public void failPayment(Payment payment) {
        System.out.println("Thanh toán đã thất bại trước đó.");
    }

    @Override
    public void refundPayment(Payment payment) {
        System.out.println("Không thể hoàn tiền cho thanh toán thất bại!");
    }

    @Override
    public String getStateName() {
        return "Thất bại";
    }
}
