// Product with State Pattern
public class Product {
    private String name;
    private double basePrice;
    private ProductTaxState taxState;

    public Product(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.taxState = new StandardTaxState(); // Default state
    }

    public void setTaxState(ProductTaxState state) {
        this.taxState = state;
    }

    public double calculateTax() {
        return taxState.calculateTax(basePrice);
    }

    public String getTaxInfo() {
        return taxState.getTaxInfo();
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getTotalPrice() {
        return basePrice + calculateTax();
    }
}
