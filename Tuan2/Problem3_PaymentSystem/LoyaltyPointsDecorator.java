// Concrete Decorator: Loyalty Points
public class LoyaltyPointsDecorator extends PaymentDecorator {
    private double pointsValue;

    public LoyaltyPointsDecorator(PaymentComponent payment, double pointsValue) {
        super(payment);
        this.pointsValue = pointsValue;
    }

    @Override
    public double getAmount() {
        return Math.max(0, payment.getAmount() - pointsValue);
    }

    @Override
    public String getDescription() {
        return payment.getDescription() + " - Điểm tích lũy (-" + pointsValue + " VND)";
    }
}
