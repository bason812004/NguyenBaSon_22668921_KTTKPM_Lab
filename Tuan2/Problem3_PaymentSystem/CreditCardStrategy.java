// Concrete Strategy: Credit Card
public class CreditCardStrategy implements PaymentMethodStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardStrategy(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Đang xử lý thanh toán qua thẻ tín dụng...");
        System.out.println("Chủ thẻ: " + cardHolderName);
        System.out.println("Số thẻ: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Số tiền: " + amount + " VND");
        // Simulate payment processing
        System.out.println("Xác thực thẻ thành công!");
        return true;
    }

    @Override
    public String getMethodName() {
        return "Thẻ tín dụng";
    }

    @Override
    public String getDescription() {
        return "Thanh toán bằng thẻ tín dụng Visa/MasterCard";
    }
}
