// Concrete Decorator: Discount Code
public class DiscountCodeDecorator extends PaymentDecorator {
    private double discountPercentage;
    private String discountCode;

    public DiscountCodeDecorator(PaymentComponent payment, String discountCode, double discountPercentage) {
        super(payment);
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getAmount() {
        return payment.getAmount() * (1 - discountPercentage);
    }

    @Override
    public String getDescription() {
        return payment.getDescription() + " - Mã giảm giá [" + discountCode + "] (-" + (discountPercentage * 100) + "%)";
    }
}
