import adapter.*;
import loadbalancer.*;
import web.*;
import java.io.IOException;

/**
 * Demo Adapter Pattern với Load Balancer và Web UI
 */
public class WebAdapterDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     ADAPTER PATTERN - LOAD BALANCER & WEB INTERFACE     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        try {
            // Khởi tạo hệ thống
            AdapterSystem system = initializeSystem();
            
            // Hiển thị thông tin ban đầu
            system.lb.displayInfo();
            
            // Chạy demo conversions
            runDemo(system);
            
            // Giữ server chạy
            keepServerRunning(system);
            
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Khởi tạo hệ thống Adapter với Load Balancer
     */
    private static AdapterSystem initializeSystem() throws IOException {
        System.out.println("🎯 Khởi tạo Load Balancer cho Adapter Servers\n");
        
        // Tạo JSON services
        JSONService jsonService1 = new JSONService();
        JSONService jsonService2 = new JSONService();
        JSONService jsonService3 = new JSONService();
        
        // Tạo adapters
        XMLService adapter1 = new XMLToJSONAdapter(jsonService1);
        XMLService adapter2 = new XMLToJSONAdapter(jsonService2);
        XMLService adapter3 = new XMLToJSONAdapter(jsonService3);
        
        // Tạo adapter servers
        AdapterServer server1 = new AdapterServer("Adapter-Server-1", 6001, adapter1);
        AdapterServer server2 = new AdapterServer("Adapter-Server-2", 6002, adapter2);
        AdapterServer server3 = new AdapterServer("Adapter-Server-3", 6003, adapter3);
        
        // Tạo load balancer
        AdapterLoadBalancer lb = new AdapterLoadBalancer("Adapter-LB");
        lb.addServer(server1);
        lb.addServer(server2);
        lb.addServer(server3);
        
        // Tạo web server
        AdapterWebServer webServer = new AdapterWebServer(5000, lb);
        webServer.start();
        
        return new AdapterSystem(lb, webServer, server1, server2, server3);
    }
    
    /**
     * Chạy demo conversions
     */
    private static void runDemo(AdapterSystem system) throws InterruptedException {
        System.out.println("\n🔄 Demo: Phân phối requests đến các servers...\n");
        
        // Test conversions - Round Robin
        for (int i = 0; i < 9; i++) {
            System.out.println("\n📨 Request #" + (i + 1) + ":");
            String xmlData = system.lb.getXMLData();
            system.webServer.logConversion("JSON → XML Conversion #" + (i + 1), "JSON data", xmlData);
            Thread.sleep(500);
        }
        
        // Demo server failure
        System.out.println("\n\n🔴 Demo: Tắt Server-2");
        system.server2.setActive(false);
        
        System.out.println("\n🔄 Tiếp tục với 6 requests...\n");
        for (int i = 0; i < 6; i++) {
            System.out.println("\n📨 Request #" + (i + 10) + ":");
            String xmlData = system.lb.getXMLData();
            system.webServer.logConversion("JSON → XML Conversion #" + (i + 10), "JSON data", xmlData);
            Thread.sleep(500);
        }
        
        // Thống kê
        System.out.println("\n\n📊 Thống kê cuối cùng:");
        system.lb.displayInfo();
    }
    
    /**
     * Giữ server chạy cho đến khi người dùng dừng
     */
    private static void keepServerRunning(AdapterSystem system) throws IOException {
        System.out.println("\n\n✅ Demo hoàn tất!");
        System.out.println("🌐 Web Server đang chạy: http://localhost:5000");
        System.out.println("📊 Truy cập web để xem conversion logs và test thêm");
        System.out.println("\n⌛ Nhấn Enter để dừng server...");
        System.in.read();
        
        system.webServer.stop();
        System.out.println("🛑 Web Server đã dừng");
    }
    
    /**
     * Class chứa các components của hệ thống
     */
    private static class AdapterSystem {
        AdapterLoadBalancer lb;
        AdapterWebServer webServer;
        AdapterServer server1;
        AdapterServer server2;
        AdapterServer server3;
        
        AdapterSystem(AdapterLoadBalancer lb, AdapterWebServer webServer,
                     AdapterServer s1, AdapterServer s2, AdapterServer s3) {
            this.lb = lb;
            this.webServer = webServer;
            this.server1 = s1;
            this.server2 = s2;
            this.server3 = s3;
        }
    }
}
