// Context for State Pattern
public class Payment {
    private PaymentState currentState;
    private double amount;
    private String paymentId;

    public Payment(String paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.currentState = new PendingPaymentState(); // Initial state
    }

    public void setState(PaymentState state) {
        this.currentState = state;
    }

    public PaymentState getState() {
        return currentState;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void process() {
        currentState.processPayment(this);
    }

    public void complete() {
        currentState.completePayment(this);
    }

    public void fail() {
        currentState.failPayment(this);
    }

    public void refund() {
        currentState.refundPayment(this);
    }
}
