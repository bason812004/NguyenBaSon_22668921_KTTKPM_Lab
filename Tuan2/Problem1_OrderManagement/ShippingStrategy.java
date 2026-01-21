// Strategy Pattern Interface
public interface ShippingStrategy {
    double calculateShippingCost(double orderAmount);
    String getShippingMethod();
}
