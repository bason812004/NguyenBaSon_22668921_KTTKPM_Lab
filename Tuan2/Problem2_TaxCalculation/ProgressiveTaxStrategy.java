// Concrete Strategy: Progressive Tax (higher price = higher rate)
public class ProgressiveTaxStrategy implements TaxCalculationStrategy {
    @Override
    public double calculateTax(double price) {
        if (price < 1000000) {
            return price * 0.05; // 5%
        } else if (price < 5000000) {
            return price * 0.10; // 10%
        } else {
            return price * 0.15; // 15%
        }
    }

    @Override
    public String getStrategyName() {
        return "Thuế lũy tiến (5%-10%-15%)";
    }
}
