package adapter;

/**
 * Adaptee - Dịch vụ hiện có chỉ hỗ trợ JSON
 */
public class JSONService {
    
    /**
     * Lấy dữ liệu ở định dạng JSON
     */
    public String getJSONData() {
        // Giả lập dữ liệu JSON
        return "{\n" +
               "  \"users\": [\n" +
               "    {\n" +
               "      \"id\": 1,\n" +
               "      \"name\": \"Nguyen Van A\",\n" +
               "      \"email\": \"nguyenvana@example.com\"\n" +
               "    },\n" +
               "    {\n" +
               "      \"id\": 2,\n" +
               "      \"name\": \"Tran Thi B\",\n" +
               "      \"email\": \"tranthib@example.com\"\n" +
               "    }\n" +
               "  ]\n" +
               "}";
    }
    
    /**
     * Xử lý dữ liệu JSON
     */
    public void processJSONData(String jsonData) {
        System.out.println("   📥 JSONService đang xử lý dữ liệu JSON:");
        System.out.println("   " + jsonData.replace("\n", "\n   "));
    }
}
