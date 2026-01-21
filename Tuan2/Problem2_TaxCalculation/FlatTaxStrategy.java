// Concrete Strategy: Flat Tax
public class FlatTaxStrategy implements TaxCalculationStrategy {
    private double rate;

    public FlatTaxStrategy(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateTax(double price) {
        return price * rate;
    }

    @Override
    public String getStrategyName() {
        return "Thuế cố định " + (rate * 100) + "%";
    }
}
