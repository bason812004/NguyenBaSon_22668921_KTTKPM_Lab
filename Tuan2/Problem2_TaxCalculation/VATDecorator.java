// Concrete Decorator: VAT Tax
public class VATDecorator extends TaxDecorator {
    private static final double VAT_RATE = 0.10;

    public VATDecorator(TaxableProduct product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + VAT_RATE);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + VAT(10%)";
    }
}
