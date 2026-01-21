// Concrete Strategy: PayPal
public class PayPalStrategy implements PaymentMethodStrategy {
    private String email;

    public PayPalStrategy(String email) {
        this.email = email;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Đang xử lý thanh toán qua PayPal...");
        System.out.println("Email: " + email);
        System.out.println("Số tiền: " + amount + " VND");
        System.out.println("Chuyển hướng đến PayPal để xác nhận...");
        System.out.println("Thanh toán PayPal thành công!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }

    @Override
    public String getDescription() {
        return "Thanh toán qua tài khoản PayPal";
    }
}
