// Abstract Decorator
public abstract class TaxDecorator implements TaxableProduct {
    protected TaxableProduct product;

    public TaxDecorator(TaxableProduct product) {
        this.product = product;
    }

    @Override
    public double getPrice() {
        return product.getPrice();
    }

    @Override
    public String getDescription() {
        return product.getDescription();
    }
}
