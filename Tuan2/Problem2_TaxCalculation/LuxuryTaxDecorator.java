// Concrete Decorator: Luxury Tax
public class LuxuryTaxDecorator extends TaxDecorator {
    private static final double LUXURY_RATE = 0.20;

    public LuxuryTaxDecorator(TaxableProduct product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + LUXURY_RATE);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + Thuế xa xỉ(20%)";
    }
}
