package loadbalancer;

import stock.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Server Observer cho Stock notifications với load balancing
 */
public class StockServer implements StockObserver, ServerInfo {
    private String name;
    private int port;
    private AtomicInteger notificationCount;
    private boolean isActive;
    
    public StockServer(String name, int port) {
        this.name = name;
        this.port = port;
        this.notificationCount = new AtomicInteger(0);
        this.isActive = true;
    }
    
    @Override
    public void update(String stockName, double price) {
        if (isActive) {
            notificationCount.incrementAndGet();
            System.out.println("   🖥️  " + getName() + " nhận thông báo: " + 
                             stockName + " = $" + price + " (Notification #" + notificationCount.get() + ")");
        }
    }
    
    public String getName() {
        return name + " (localhost:" + port + ")";
    }
    
    public int getNotificationCount() {
        return notificationCount.get();
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public int getPort() {
        return port;
    }
    
    public void displayInfo() {
        String status = isActive ? "🟢 ACTIVE" : "🔴 INACTIVE";
        System.out.println("   🖥️  " + getName() + " - " + status);
        System.out.println("      📊 Notifications: " + notificationCount.get());
    }
}
