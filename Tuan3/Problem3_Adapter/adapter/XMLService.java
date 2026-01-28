package adapter;

/**
 * Target interface - Interface mà client (hệ thống yêu cầu XML) sử dụng
 */
public interface XMLService {
    /**
     * Lấy dữ liệu ở định dạng XML
     */
    String getXMLData();
    
    /**
     * Xử lý dữ liệu XML
     */
    void processXMLData(String xmlData);
}
