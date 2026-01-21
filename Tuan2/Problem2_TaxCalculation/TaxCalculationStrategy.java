// Strategy Pattern Interface for Tax Calculation
public interface TaxCalculationStrategy {
    double calculateTax(double price);
    String getStrategyName();
}
