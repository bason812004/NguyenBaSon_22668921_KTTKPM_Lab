// Concrete Decorator: Insurance
public class InsuranceDecorator extends OrderDecorator {
    public InsuranceDecorator(OrderComponent order) {
        super(order);
    }

    @Override
    public double getCost() {
        return decoratedOrder.getCost() + (decoratedOrder.getCost() * 0.02); // 2% insurance
    }

    @Override
    public String getDescription() {
        return decoratedOrder.getDescription() + " + Bảo hiểm vận chuyển";
    }
}
