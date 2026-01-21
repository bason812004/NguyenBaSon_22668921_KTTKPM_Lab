// Concrete State: Delivered
public class DeliveredState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Đơn hàng #" + order.getOrderId() + " đã được giao.");
        System.out.println("Cập nhật trạng thái: Đã giao.");
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Không thể hủy đơn hàng đã giao!");
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Đơn hàng đã được giao trước đó.");
    }

    @Override
    public String getStateName() {
        return "Đã giao";
    }
}
