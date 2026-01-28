import stock.*;
import loadbalancer.*;
import web.*;
import java.io.IOException;
import java.util.Random;

/**
 * Demo Observer Pattern với Load Balancer và Web UI
 */
public class WebObserverDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║    OBSERVER PATTERN - LOAD BALANCER & WEB DASHBOARD     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        try {
            // Khởi tạo hệ thống
            ObserverSystem system = initializeSystem();
            
            // Hiển thị thông tin ban đầu
            system.lb.displayInfo();
            
            // Chạy demo stock monitoring
            runStockSimulation(system);
            
            // Giữ server chạy
            keepServerRunning(system);
            
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Khởi tạo hệ thống Observer với Load Balancer
     */
    private static ObserverSystem initializeSystem() throws IOException {
        System.out.println("🎯 Khởi tạo Load Balancer với Round Robin Strategy\n");
        
        // Tạo stocks
        Stock appleStock = new Stock("AAPL", 150.0);
        Stock googleStock = new Stock("GOOGL", 2800.0);
        Stock teslaStock = new Stock("TSLA", 700.0);
        Stock amazonStock = new Stock("AMZN", 3200.0);
        
        // Tạo web server
        StockWebServer webServer = new StockWebServer(4000);
        webServer.addStock(appleStock);
        webServer.addStock(googleStock);
        webServer.addStock(teslaStock);
        webServer.addStock(amazonStock);
        
        // Tạo Load Balancer
        StockLoadBalancer lb = new StockLoadBalancer("Stock-LB", new RoundRobinStrategy());
        
        // Tạo servers
        StockServer server1 = new StockServer("Server-1", 5001);
        StockServer server2 = new StockServer("Server-2", 5002);
        StockServer server3 = new StockServer("Server-3", 5003);
        
        lb.addServer(server1);
        lb.addServer(server2);
        lb.addServer(server3);
        
        // Tạo Web Observer để ghi lại updates
        StockObserver webObserver = new StockObserver() {
            @Override
            public void update(String stockName, double price) {
                webServer.recordUpdate(stockName, price, "Web Dashboard");
            }
        };
        
        // Đăng ký observers cho tất cả stocks
        Stock[] stocks = {appleStock, googleStock, teslaStock, amazonStock};
        for (Stock stock : stocks) {
            stock.attach(lb);
            stock.attach(webObserver);
        }
        
        // Start web server
        webServer.start();
        
        return new ObserverSystem(lb, webServer, appleStock, googleStock, teslaStock, amazonStock);
    }
    
    /**
     * Chạy mô phỏng thay đổi giá cổ phiếu
     */
    private static void runStockSimulation(ObserverSystem system) throws InterruptedException {
        System.out.println("\n🔄 Bắt đầu mô phỏng thay đổi giá cổ phiếu...\n");
        
        Random random = new Random();
        Stock[] stocks = {system.appleStock, system.googleStock, system.teslaStock, system.amazonStock};
        double[] priceChanges = {10, 100, 20, 50}; // Max change for each stock
        
        for (int i = 0; i < 15; i++) {
            Thread.sleep(1500);
            
            // Random price change
            int index = random.nextInt(4);
            Stock selectedStock = stocks[index];
            double change = random.nextDouble() * priceChanges[index] - (priceChanges[index] / 2);
            selectedStock.setPrice(selectedStock.getPrice() + change);
        }
        
        // Thống kê
        System.out.println("\n\n📊 Thống kê cuối cùng:");
        system.lb.displayInfo();
    }
    
    /**
     * Giữ server chạy cho đến khi người dùng dừng
     */
    private static void keepServerRunning(ObserverSystem system) throws IOException {
        System.out.println("\n\n✅ Mô phỏng hoàn tất!");
        System.out.println("🌐 Web Dashboard đang chạy: http://localhost:4000");
        System.out.println("📈 Trang web tự động refresh mỗi 2 giây");
        System.out.println("📊 Xem real-time stock prices và notifications");
        System.out.println("\n⌛ Nhấn Enter để dừng server...");
        System.in.read();
        
        system.webServer.stop();
        System.out.println("🛑 Web Server đã dừng");
    }
    
    /**
     * Class chứa các components của hệ thống
     */
    private static class ObserverSystem {
        StockLoadBalancer lb;
        StockWebServer webServer;
        Stock appleStock;
        Stock googleStock;
        Stock teslaStock;
        Stock amazonStock;
        
        ObserverSystem(StockLoadBalancer lb, StockWebServer webServer,
                      Stock apple, Stock google, Stock tesla, Stock amazon) {
            this.lb = lb;
            this.webServer = webServer;
            this.appleStock = apple;
            this.googleStock = google;
            this.teslaStock = tesla;
            this.amazonStock = amazon;
        }
    }
}
