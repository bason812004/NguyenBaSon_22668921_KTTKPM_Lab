// Concrete Component
public class SimpleProduct implements TaxableProduct {
    private String name;
    private double price;

    public SimpleProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }
}
