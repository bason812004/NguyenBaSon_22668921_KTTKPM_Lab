// Concrete State: Cancelled
public class CancelledState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Đơn hàng #" + order.getOrderId() + " đã bị hủy.");
        System.out.println("Không thể xử lý đơn hàng đã hủy.");
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Đơn hàng đã bị hủy trước đó.");
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Không thể giao đơn hàng đã hủy!");
    }

    @Override
    public String getStateName() {
        return "Đã hủy";
    }
}
