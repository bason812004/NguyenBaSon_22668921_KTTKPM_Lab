// Concrete Decorator: Priority Processing
public class PriorityProcessingDecorator extends OrderDecorator {
    public PriorityProcessingDecorator(OrderComponent order) {
        super(order);
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost() + 25000;
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Xử lý ưu tiên";
    }
}
