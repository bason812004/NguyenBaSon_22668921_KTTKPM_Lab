// Abstract Decorator
public abstract class OrderDecorator implements OrderComponent {
    protected OrderComponent decoratedOrder;

    public OrderDecorator(OrderComponent order) {
        this.decoratedOrder = order;
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost();
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription();
    }
}
