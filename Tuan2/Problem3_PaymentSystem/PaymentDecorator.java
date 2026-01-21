// Abstract Decorator
public abstract class PaymentDecorator implements PaymentComponent {
    protected PaymentComponent payment;

    public PaymentDecorator(PaymentComponent payment) {
        this.payment = payment;
    }

    @Override
    public double getAmount() {
        return payment.getAmount();
    }

    @Override
    public String getDescription() {
        return payment.getDescription();
    }
}
