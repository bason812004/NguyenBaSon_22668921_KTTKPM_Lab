// Concrete State: Processing
public class ProcessingState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Đang đóng gói và vận chuyển đơn hàng #" + order.getOrderId());
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Hủy đơn hàng #" + order.getOrderId() + " đang xử lý.");
        System.out.println("Hoàn tiền: " + order.getAmount() + " VND");
        order.setState(new CancelledState());
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Giao hàng thành công cho đơn hàng #" + order.getOrderId());
        order.setState(new DeliveredState());
    }

    @Override
    public String getStateName() {
        return "Đang xử lý";
    }
}
