// Concrete State: Standard Tax (No special tax)
public class StandardTaxState implements ProductTaxState {
    @Override
    public double calculateTax(double basePrice) {
        return 0; // No tax
    }

    @Override
    public String getTaxInfo() {
        return "Sản phẩm thường - Không áp thuế đặc biệt";
    }
}
