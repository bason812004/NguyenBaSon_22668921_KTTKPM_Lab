// State Pattern Interface
public interface OrderState {
    void handleOrder(Order order);
    void cancelOrder(Order order);
    void deliverOrder(Order order);
    String getStateName();
}
