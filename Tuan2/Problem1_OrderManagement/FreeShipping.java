// Concrete Strategy: Free Shipping (for orders over 500k)
public class FreeShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderAmount) {
        return orderAmount >= 500000 ? 0 : 30000;
    }

    @Override
    public String getShippingMethod() {
        return "Miễn phí vận chuyển (đơn hàng >= 500k)";
    }
}
