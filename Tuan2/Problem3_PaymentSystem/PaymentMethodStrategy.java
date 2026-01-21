// Strategy Pattern Interface
public interface PaymentMethodStrategy {
    boolean processPayment(double amount);
    String getMethodName();
    String getDescription();
}
