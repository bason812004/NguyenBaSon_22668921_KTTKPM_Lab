// State Pattern Interface
public interface PaymentState {
    void processPayment(Payment payment);
    void completePayment(Payment payment);
    void failPayment(Payment payment);
    void refundPayment(Payment payment);
    String getStateName();
}
