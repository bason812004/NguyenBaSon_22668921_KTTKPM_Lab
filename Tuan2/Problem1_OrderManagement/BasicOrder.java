// Concrete Component
public class BasicOrder implements OrderComponent {
    private double basePrice;
    private String description;

    public BasicOrder(double basePrice, String description) {
        this.basePrice = basePrice;
        this.description = description;
    }

    @Override
    public double getCost() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
