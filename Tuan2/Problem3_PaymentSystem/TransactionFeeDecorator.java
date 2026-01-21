// Concrete Decorator: Transaction Fee
public class TransactionFeeDecorator extends PaymentDecorator {
    private double fixedFee;

    public TransactionFeeDecorator(PaymentComponent payment, double fixedFee) {
        super(payment);
        this.fixedFee = fixedFee;
    }

    @Override
    public double getAmount() {
        return payment.getAmount() + fixedFee;
    }

    @Override
    public String getDescription() {
        return payment.getDescription() + " + Phí giao dịch (+" + fixedFee + " VND)";
    }
}
