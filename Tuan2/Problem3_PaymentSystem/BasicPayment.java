// Concrete Component
public class BasicPayment implements PaymentComponent {
    private double amount;
    private String description;

    public BasicPayment(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
