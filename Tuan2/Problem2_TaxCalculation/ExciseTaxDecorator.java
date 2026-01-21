// Concrete Decorator: Excise Tax
public class ExciseTaxDecorator extends TaxDecorator {
    private static final double EXCISE_RATE = 0.35;

    public ExciseTaxDecorator(TaxableProduct product) {
        super(product);
    }

    @Override
    public double getPrice() {
        return product.getPrice() * (1 + EXCISE_RATE);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " + Thuế tiêu thụ đặc biệt(35%)";
    }
}
