// Concrete State: Excise Tax State (for tobacco, alcohol, etc.)
public class ExciseTaxState implements ProductTaxState {
    @Override
    public double calculateTax(double basePrice) {
        return basePrice * 0.35; // 35% excise tax
    }

    @Override
    public String getTaxInfo() {
        return "Thuế tiêu thụ đặc biệt 35%";
    }
}
