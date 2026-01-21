// Concrete Strategy: Bank Transfer
public class BankTransferStrategy implements PaymentMethodStrategy {
    private String accountNumber;
    private String bankName;

    public BankTransferStrategy(String accountNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Đang xử lý chuyển khoản ngân hàng...");
        System.out.println("Ngân hàng: " + bankName);
        System.out.println("Số tài khoản: " + accountNumber);
        System.out.println("Số tiền: " + amount + " VND");
        System.out.println("Đang chờ xác nhận từ ngân hàng...");
        System.out.println("Chuyển khoản thành công!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Chuyển khoản ngân hàng";
    }

    @Override
    public String getDescription() {
        return "Thanh toán bằng chuyển khoản ngân hàng";
    }
}
