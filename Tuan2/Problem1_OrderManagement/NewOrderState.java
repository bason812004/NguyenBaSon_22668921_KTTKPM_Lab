// Concrete State: New Order
public class NewOrderState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        System.out.println("Kiểm tra thông tin đơn hàng #" + order.getOrderId());
        System.out.println("Thông tin hợp lệ. Chuyển sang trạng thái Đang xử lý.");
        order.setState(new ProcessingState());
    }

    @Override
    public void cancelOrder(Order order) {
        System.out.println("Hủy đơn hàng #" + order.getOrderId());
        order.setState(new CancelledState());
    }

    @Override
    public void deliverOrder(Order order) {
        System.out.println("Không thể giao đơn hàng chưa được xử lý!");
    }

    @Override
    public String getStateName() {
        return "Mới tạo";
    }
}
