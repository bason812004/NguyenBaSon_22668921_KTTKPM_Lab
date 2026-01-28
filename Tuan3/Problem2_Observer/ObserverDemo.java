import stock.*;
import task.*;

/**
 * Demo class cho Observer Design Pattern
 * Minh họa cả hai trường hợp: Stock Notification và Task Status Notification
 */
public class ObserverDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          OBSERVER DESIGN PATTERN DEMONSTRATION           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        // Demo 1: Stock Observer
        demoStockObserver();
        
        System.out.println("\n");
        
        // Demo 2: Task Observer
        demoTaskObserver();
    }
    
    /**
     * Demo Observer Pattern với hệ thống theo dõi cổ phiếu
     */
    private static void demoStockObserver() {
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  DEMO 1: STOCK PRICE NOTIFICATION");
        System.out.println("  (Thông báo thay đổi giá cổ phiếu cho nhà đầu tư)");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo cổ phiếu
        Stock appleStock = new Stock("AAPL", 150.0);
        Stock googleStock = new Stock("GOOGL", 2800.0);
        
        // Tạo các nhà đầu tư
        Investor investor1 = new Investor("Nguyễn Văn A");
        Investor investor2 = new Investor("Trần Thị B");
        Investor investor3 = new Investor("Lê Văn C");
        
        System.out.println("📌 Đăng ký theo dõi cổ phiếu:");
        
        // Đăng ký theo dõi
        appleStock.attach(investor1);
        appleStock.attach(investor2);
        googleStock.attach(investor2);
        googleStock.attach(investor3);
        
        System.out.println("\n📊 Thay đổi giá cổ phiếu:");
        
        // Thay đổi giá
        appleStock.setPrice(155.50);  // Tăng
        googleStock.setPrice(2750.0); // Giảm
        
        System.out.println("\n📌 Nhà đầu tư A hủy theo dõi AAPL:");
        appleStock.detach(investor1);
        
        // Thay đổi giá lần nữa
        appleStock.setPrice(160.0);
    }
    
    /**
     * Demo Observer Pattern với hệ thống quản lý task
     */
    private static void demoTaskObserver() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  DEMO 2: TASK STATUS NOTIFICATION");
        System.out.println("  (Thông báo thay đổi trạng thái công việc cho thành viên)");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo task
        Task loginFeature = new Task("Implement Login Feature");
        Task apiIntegration = new Task("API Integration");
        
        // Tạo các thành viên nhóm
        TeamMember dev1 = new TeamMember("Nguyễn Developer", "Backend Developer");
        TeamMember dev2 = new TeamMember("Trần Developer", "Frontend Developer");
        TeamMember pm = new TeamMember("Lê Manager", "Project Manager");
        TeamMember qa = new TeamMember("Phạm Tester", "QA Engineer");
        
        System.out.println("📌 Thêm thành viên vào theo dõi task:");
        
        // Đăng ký theo dõi
        loginFeature.attach(dev1);
        loginFeature.attach(dev2);
        loginFeature.attach(pm);
        loginFeature.attach(qa);
        
        apiIntegration.attach(dev1);
        apiIntegration.attach(pm);
        
        System.out.println("\n📋 Workflow của task 'Login Feature':");
        
        // Cập nhật trạng thái task
        loginFeature.setStatus(Task.STATUS_IN_PROGRESS);
        
        System.out.println("\n⏳ [Sau 2 ngày làm việc...]");
        loginFeature.setStatus(Task.STATUS_REVIEW);
        
        System.out.println("\n⏳ [Sau khi review xong...]");
        loginFeature.setStatus(Task.STATUS_DONE);
        
        System.out.println("\n📋 Workflow của task 'API Integration':");
        apiIntegration.setStatus(Task.STATUS_IN_PROGRESS);
        apiIntegration.setStatus(Task.STATUS_BLOCKED);
    }
}
