import filesystem.*;
import ui.*;
import loadbalancer.*;

/**
 * Demo class cho Composite Design Pattern
 * Minh họa cả ba trường hợp: File System, UI Components, và Load Balancer
 */
public class CompositeDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║         COMPOSITE DESIGN PATTERN DEMONSTRATION           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        // Demo 1: File System
        demoFileSystem();
        
        System.out.println("\n");
        
        // Demo 2: UI Components
        demoUIComponents();
        
        System.out.println("\n");
        
        // Demo 3: Load Balancer
        demoLoadBalancer();
    }
    
    /**
     * Demo Composite Pattern với hệ thống quản lý thư mục và tập tin
     */
    private static void demoFileSystem() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  DEMO 1: FILE SYSTEM (Quản lý thư mục và tập tin)");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo cấu trúc thư mục
        Folder root = new Folder("Project");
        
        // Thư mục src
        Folder src = new Folder("src");
        src.add(new File("Main.java", 2048));
        src.add(new File("Utils.java", 1024));
        
        // Thư mục models trong src
        Folder models = new Folder("models");
        models.add(new File("User.java", 512));
        models.add(new File("Product.java", 768));
        src.add(models);
        
        // Thư mục docs
        Folder docs = new Folder("docs");
        docs.add(new File("README.md", 256));
        docs.add(new File("API.md", 512));
        
        // Thêm vào root
        root.add(src);
        root.add(docs);
        root.add(new File("pom.xml", 1024));
        
        // Hiển thị cấu trúc cây
        System.out.println("📂 Cấu trúc thư mục:\n");
        root.display("");
        
        System.out.println("\n📊 Thống kê:");
        System.out.println("   - Tổng kích thước Project: " + root.getSize() + " bytes");
        System.out.println("   - Kích thước thư mục src: " + src.getSize() + " bytes");
        System.out.println("   - Kích thước thư mục models: " + models.getSize() + " bytes");
    }
    
    /**
     * Demo Composite Pattern với các thành phần UI
     */
    private static void demoUIComponents() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  DEMO 2: UI COMPONENTS (Giao diện người dùng)");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo Dialog đăng nhập
        Dialog loginDialog = new Dialog("Đăng Nhập Hệ Thống");
        
        // Panel form đăng nhập
        Panel formPanel = new Panel("Form Đăng Nhập");
        formPanel.add(new TextField("txtUsername", "Nhập tên đăng nhập..."));
        formPanel.add(new TextField("txtPassword", "Nhập mật khẩu..."));
        
        // Panel các nút
        Panel buttonPanel = new Panel("Các Nút Điều Khiển");
        buttonPanel.add(new Button("btnLogin", "Đăng Nhập"));
        buttonPanel.add(new Button("btnCancel", "Hủy Bỏ"));
        buttonPanel.add(new Button("btnForgot", "Quên Mật Khẩu"));
        
        // Thêm vào dialog
        loginDialog.add(formPanel);
        loginDialog.add(buttonPanel);
        
        // Render dialog
        System.out.println("🖥️ Giao diện Login Dialog:\n");
        loginDialog.render();
        
        System.out.println("\n\n🎛️ Tạo Navigation Bar riêng:\n");
        
        // Tạo thanh điều hướng
        Panel navBar = new Panel("Navigation Bar");
        navBar.add(new Button("btnHome", "🏠 Trang Chủ"));
        navBar.add(new Button("btnProducts", "📦 Sản Phẩm"));
        navBar.add(new Button("btnAbout", "ℹ️ Giới Thiệu"));
        navBar.add(new Button("btnContact", "📞 Liên Hệ"));
        
        navBar.render();
    }
    
    /**
     * Demo Composite Pattern với Load Balancer trên localhost
     */
    private static void demoLoadBalancer() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  DEMO 3: LOAD BALANCER (Cân bằng tải trên localhost)");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo filesystem cho các servers
        Folder server1Data = new Folder("server1_data");
        server1Data.add(new File("database.db", 5120));
        server1Data.add(new File("cache.dat", 2048));
        
        Folder server2Data = new Folder("server2_data");
        server2Data.add(new File("images.zip", 8192));
        server2Data.add(new File("videos.mp4", 10240));
        
        Folder server3Data = new Folder("server3_data");
        server3Data.add(new File("logs.txt", 1024));
        server3Data.add(new File("config.json", 512));
        
        // Tạo các servers trên localhost với ports khác nhau
        Server server1 = new Server("Server-1", 8080, server1Data);
        Server server2 = new Server("Server-2", 8081, server2Data);
        Server server3 = new Server("Server-3", 8082, server3Data);
        
        System.out.println("🎯 CHIẾN LƯỢC 1: ROUND ROBIN");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        // Tạo Load Balancer với Round Robin strategy
        LoadBalancer lb1 = new LoadBalancer("Primary-LB", new RoundRobinStrategy());
        lb1.addServer(server1);
        lb1.addServer(server2);
        lb1.addServer(server3);
        
        lb1.display("");
        lb1.distributeRequests(9);
        
        System.out.println("\n📊 Thống kê sau Round Robin:");
        System.out.println("   Server-1 (localhost:8080): " + server1.getRequestCount() + " requests");
        System.out.println("   Server-2 (localhost:8081): " + server2.getRequestCount() + " requests");
        System.out.println("   Server-3 (localhost:8082): " + server3.getRequestCount() + " requests");
        
        System.out.println("\n\n🎯 CHIẾN LƯỢC 2: LEAST CONNECTION");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        // Reset và chuyển sang Least Connection strategy
        LoadBalancer lb2 = new LoadBalancer("Secondary-LB", new LeastConnectionStrategy());
        
        // Tạo servers mới với trạng thái ban đầu khác nhau
        Server server4 = new Server("Server-4", 9080, server1Data);
        Server server5 = new Server("Server-5", 9081, server2Data);
        Server server6 = new Server("Server-6", 9082, server3Data);
        
        // Giả lập server đã có requests từ trước
        for (int i = 0; i < 3; i++) server4.handleRequest();
        for (int i = 0; i < 1; i++) server5.handleRequest();
        
        lb2.addServer(server4);
        lb2.addServer(server5);
        lb2.addServer(server6);
        
        System.out.println("📍 Trạng thái ban đầu:");
        System.out.println("   Server-4 (localhost:9080): " + server4.getRequestCount() + " requests");
        System.out.println("   Server-5 (localhost:9081): " + server5.getRequestCount() + " requests");
        System.out.println("   Server-6 (localhost:9082): " + server6.getRequestCount() + " requests");
        
        lb2.distributeRequests(6);
        
        System.out.println("\n📊 Thống kê sau Least Connection:");
        System.out.println("   Server-4 (localhost:9080): " + server4.getRequestCount() + " requests");
        System.out.println("   Server-5 (localhost:9081): " + server5.getRequestCount() + " requests");
        System.out.println("   Server-6 (localhost:9082): " + server6.getRequestCount() + " requests");
        
        System.out.println("\n\n🎯 CHIẾN LƯỢC 3: RANDOM");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        // Tạo Load Balancer với Random strategy
        LoadBalancer lb3 = new LoadBalancer("Tertiary-LB", new RandomStrategy());
        
        Server server7 = new Server("Server-7", 7080, server1Data);
        Server server8 = new Server("Server-7", 7081, server2Data);
        
        lb3.addServer(server7);
        lb3.addServer(server8);
        
        lb3.distributeRequests(8);
        
        System.out.println("\n📊 Thống kê sau Random:");
        System.out.println("   Server-7 (localhost:7080): " + server7.getRequestCount() + " requests");
        System.out.println("   Server-7 (localhost:7081): " + server8.getRequestCount() + " requests");
        
        System.out.println("\n\n🔴 DEMO: SERVER FAILURE");
        System.out.println("─────────────────────────────────────────────────────────\n");
        
        // Tắt một server
        server2.setActive(false);
        System.out.println("⚠️  Server-2 (localhost:8081) đã bị tắt!");
        
        System.out.println("\n🔄 Phân phối lại 6 requests với Round Robin:");
        lb1.distributeRequests(6);
        
        System.out.println("\n📊 Thống kê cuối cùng:");
        System.out.println("   Server-1 (localhost:8080): " + server1.getRequestCount() + " requests");
        System.out.println("   Server-2 (localhost:8081): " + server2.getRequestCount() + " requests [INACTIVE]");
        System.out.println("   Server-3 (localhost:8082): " + server3.getRequestCount() + " requests");
        
        System.out.println("\n💾 Tổng dữ liệu được quản lý: " + lb1.getSize() + " bytes");
    }
}
