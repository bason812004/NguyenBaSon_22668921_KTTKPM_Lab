// State Pattern Interface for Tax States
public interface ProductTaxState {
    double calculateTax(double basePrice);
    String getTaxInfo();
}
