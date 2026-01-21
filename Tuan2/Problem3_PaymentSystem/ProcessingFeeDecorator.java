// Concrete Decorator: Processing Fee
public class ProcessingFeeDecorator extends PaymentDecorator {
    private double feePercentage;

    public ProcessingFeeDecorator(PaymentComponent payment, double feePercentage) {
        super(payment);
        this.feePercentage = feePercentage;
    }

    @Override
    public double getAmount() {
        return payment.getAmount() * (1 + feePercentage);
    }

    @Override
    public String getDescription() {
        return payment.getDescription() + " + Phí xử lý (" + (feePercentage * 100) + "%)";
    }
}
