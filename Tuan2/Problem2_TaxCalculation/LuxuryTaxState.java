// Concrete State: Luxury Tax State
public class LuxuryTaxState implements ProductTaxState {
    @Override
    public double calculateTax(double basePrice) {
        return basePrice * 0.20; // 20% luxury tax
    }

    @Override
    public String getTaxInfo() {
        return "Thuế sản phẩm xa xỉ 20%";
    }
}
