// Concrete State: VAT Tax State
public class VATTaxState implements ProductTaxState {
    @Override
    public double calculateTax(double basePrice) {
        return basePrice * 0.10; // 10% VAT
    }

    @Override
    public String getTaxInfo() {
        return "Thuế VAT 10%";
    }
}
