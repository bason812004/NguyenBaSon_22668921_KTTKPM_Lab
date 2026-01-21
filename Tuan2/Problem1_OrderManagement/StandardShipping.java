// Concrete Strategy: Standard Shipping
public class StandardShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return 30000; // Fixed cost
    }

    @Override
    public String getShippingMethod() {
        return "Giao hàng tiêu chuẩn (3-5 ngày)";
    }
}
