// Concrete Strategy: Import Tax (for imported products)
public class ImportTaxStrategy implements TaxCalculationStrategy {
    @Override
    public double calculateTax(double price) {
        return price * 0.25; // 25% import tax
    }

    @Override
    public String getStrategyName() {
        return "Thuế nhập khẩu 25%";
    }
}
