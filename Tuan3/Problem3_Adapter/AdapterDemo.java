import adapter.*;

/**
 * Demo class cho Adapter Design Pattern
 * Minh họa chuyển đổi giữa định dạng JSON và XML
 */
public class AdapterDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          ADAPTER DESIGN PATTERN DEMONSTRATION            ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("  SCENARIO: Hệ thống A yêu cầu XML, nhưng Hệ thống B chỉ");
        System.out.println("  hỗ trợ JSON. Sử dụng Adapter để chuyển đổi.");
        System.out.println("═══════════════════════════════════════════════════════════\n");
        
        // Tạo dịch vụ JSON (Adaptee)
        JSONService jsonService = new JSONService();
        
        // Tạo Adapter
        XMLService adapter = new XMLToJSONAdapter(jsonService);
        
        // Demo 1: Lấy dữ liệu từ JSON service dưới dạng XML
        demoGetXMLFromJSON(adapter);
        
        System.out.println("\n");
        
        // Demo 2: Gửi dữ liệu XML để xử lý bằng JSON service
        demoProcessXMLWithJSON(adapter);
    }
    
    /**
     * Demo: Lấy dữ liệu XML từ JSONService thông qua Adapter
     */
    private static void demoGetXMLFromJSON(XMLService xmlService) {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│ DEMO 1: Lấy dữ liệu XML từ JSON Service                    │");
        System.out.println("└────────────────────────────────────────────────────────────┘\n");
        
        System.out.println("   📤 Client yêu cầu dữ liệu XML...\n");
        
        String xmlData = xmlService.getXMLData();
        
        System.out.println("   📄 Kết quả XML nhận được:\n");
        System.out.println("   " + xmlData.replace("\n", "\n   "));
    }
    
    /**
     * Demo: Gửi dữ liệu XML để xử lý qua JSON Service
     */
    private static void demoProcessXMLWithJSON(XMLService xmlService) {
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│ DEMO 2: Xử lý dữ liệu XML thông qua JSON Service           │");
        System.out.println("└────────────────────────────────────────────────────────────┘\n");
        
        String inputXML = "<?xml version=\"1.0\"?>\n" +
                         "<products>\n" +
                         "  <product id=\"101\">\n" +
                         "    <name>Laptop</name>\n" +
                         "    <price>15000000</price>\n" +
                         "  </product>\n" +
                         "  <product id=\"102\">\n" +
                         "    <name>Smartphone</name>\n" +
                         "    <price>8000000</price>\n" +
                         "  </product>\n" +
                         "</products>";
        
        System.out.println("   📤 Client gửi dữ liệu XML để xử lý:\n");
        System.out.println("   " + inputXML.replace("\n", "\n   "));
        System.out.println();
        
        xmlService.processXMLData(inputXML);
        
        System.out.println("\n   ✅ Xử lý thành công!");
    }
}
