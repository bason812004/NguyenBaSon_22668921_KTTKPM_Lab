// Context class for State Pattern
public class Order {
    private OrderState currentState;
    private String orderId;
    private double amount;

    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
        this.currentState = new NewOrderState(); // Initial state
    }

    public void setState(OrderState state) {
        this.currentState = state;
    }

    public OrderState getState() {
        return currentState;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void process() {
        currentState.handleOrder(this);
    }

    public void cancel() {
        currentState.cancelOrder(this);
    }

    public void deliver() {
        currentState.deliverOrder(this);
    }
}
