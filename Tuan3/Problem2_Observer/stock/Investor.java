package stock;

/**
 * Concrete Observer - Nhà đầu tư theo dõi cổ phiếu
 */
public class Investor implements StockObserver {
    private String name;
    
    public Investor(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String stockName, double price) {
        System.out.println("      → " + name + " nhận thông báo: " + stockName + " = $" + price);
    }
    
    public String getName() {
        return name;
    }
}
