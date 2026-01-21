// Concrete Strategy: Express Shipping
public class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return 60000; // Higher fixed cost
    }

    @Override
    public String getShippingMethod() {
        return "Giao hàng nhanh (1-2 ngày)";
    }
}
