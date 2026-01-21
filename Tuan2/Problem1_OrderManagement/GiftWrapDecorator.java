// Concrete Decorator: Gift Wrap
public class GiftWrapDecorator extends OrderDecorator {
    public GiftWrapDecorator(OrderComponent order) {
        super(order);
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost() + 15000;
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Gói quà";
    }
}
