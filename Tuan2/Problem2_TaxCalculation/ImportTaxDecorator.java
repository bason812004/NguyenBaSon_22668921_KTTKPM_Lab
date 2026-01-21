// Concrete Decorator: Import Tax
public class ImportTaxDecorator extends TaxDecorator {
    private static final double IMPORT_RATE = 0.25;

    public ImportTaxDecorator(TaxableProduct product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + IMPORT_RATE);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + Thuế nhập khẩu(25%)";
    }
}
