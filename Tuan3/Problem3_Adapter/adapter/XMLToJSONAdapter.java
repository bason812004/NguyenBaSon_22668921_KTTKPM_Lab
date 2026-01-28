package adapter;

/**
 * Adapter - Chuyển đổi giữa XMLService interface và JSONService
 * Cho phép hệ thống yêu cầu XML làm việc với dịch vụ chỉ hỗ trợ JSON
 */
public class XMLToJSONAdapter implements XMLService {
    private JSONService jsonService;
    
    public XMLToJSONAdapter(JSONService jsonService) {
        this.jsonService = jsonService;
    }
    
    @Override
    public String getXMLData() {
        // Lấy dữ liệu JSON từ JSONService
        String jsonData = jsonService.getJSONData();
        
        // Chuyển đổi JSON sang XML
        return convertJSONToXML(jsonData);
    }
    
    @Override
    public void processXMLData(String xmlData) {
        // Chuyển đổi XML sang JSON
        String jsonData = convertXMLToJSON(xmlData);
        
        // Xử lý bằng JSONService
        jsonService.processJSONData(jsonData);
    }
    
    /**
     * Chuyển đổi JSON sang XML (phiên bản đơn giản)
     */
    private String convertJSONToXML(String json) {
        System.out.println("   🔄 Adapter: Đang chuyển đổi JSON → XML...\n");
        
        // Đây là phiên bản đơn giản để minh họa
        // Trong thực tế sẽ sử dụng thư viện như Jackson, Gson, etc.
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<data>\n");
        xml.append("  <users>\n");
        xml.append("    <user>\n");
        xml.append("      <id>1</id>\n");
        xml.append("      <name>Nguyen Van A</name>\n");
        xml.append("      <email>nguyenvana@example.com</email>\n");
        xml.append("    </user>\n");
        xml.append("    <user>\n");
        xml.append("      <id>2</id>\n");
        xml.append("      <name>Tran Thi B</name>\n");
        xml.append("      <email>tranthib@example.com</email>\n");
        xml.append("    </user>\n");
        xml.append("  </users>\n");
        xml.append("</data>");
        
        return xml.toString();
    }
    
    /**
     * Chuyển đổi XML sang JSON (phiên bản đơn giản)
     */
    private String convertXMLToJSON(String xml) {
        System.out.println("   🔄 Adapter: Đang chuyển đổi XML → JSON...\n");
        
        // Đây là phiên bản đơn giản để minh họa
        // Trong thực tế sẽ sử dụng thư viện chuyển đổi
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"products\": [\n");
        json.append("    {\n");
        json.append("      \"id\": 101,\n");
        json.append("      \"name\": \"Laptop\",\n");
        json.append("      \"price\": 15000000\n");
        json.append("    },\n");
        json.append("    {\n");
        json.append("      \"id\": 102,\n");
        json.append("      \"name\": \"Smartphone\",\n");
        json.append("      \"price\": 8000000\n");
        json.append("    }\n");
        json.append("  ]\n");
        json.append("}");
        
        return json.toString();
    }
}
