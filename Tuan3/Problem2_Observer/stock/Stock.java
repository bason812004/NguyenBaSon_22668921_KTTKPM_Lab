package stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject trong Observer Pattern - Đại diện cho một cổ phiếu
 * Khi giá thay đổi, tất cả các observer (nhà đầu tư) sẽ được thông báo
 */
public class Stock {
    private String name;
    private double price;
    private List<StockObserver> observers;
    
    public Stock(String name, double initialPrice) {
        this.name = name;
        this.price = initialPrice;
        this.observers = new ArrayList<>();
    }
    
    /**
     * Đăng ký một observer mới
     */
    public void attach(StockObserver observer) {
        observers.add(observer);
        System.out.println("   ✅ Đã đăng ký theo dõi cổ phiếu " + name);
    }
    
    /**
     * Hủy đăng ký một observer
     */
    public void detach(StockObserver observer) {
        observers.remove(observer);
        System.out.println("   ❌ Đã hủy theo dõi cổ phiếu " + name);
    }
    
    /**
     * Thông báo cho tất cả observers khi có thay đổi
     */
    public void notifyObservers() {
        System.out.println("\n   📢 Thông báo đến " + observers.size() + " nhà đầu tư:");
        for (StockObserver observer : observers) {
            observer.update(name, price);
        }
    }
    
    /**
     * Cập nhật giá cổ phiếu và thông báo cho observers
     */
    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        
        String trend = newPrice > oldPrice ? "📈 TĂNG" : (newPrice < oldPrice ? "📉 GIẢM" : "➡️ GIỮ NGUYÊN");
        System.out.println("\n   💰 Cổ phiếu " + name + ": " + oldPrice + " → " + newPrice + " (" + trend + ")");
        
        notifyObservers();
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getObserverCount() {
        return observers.size();
    }
}
