package loadbalancer;

import adapter.*;

/**
 * Adapter Server với load balancing
 */
public class AdapterServer implements XMLService {
    private String name;
    private int port;
    private XMLService adaptee;
    private int requestCount;
    private boolean isActive;
    
    public AdapterServer(String name, int port, XMLService adaptee) {
        this.name = name;
        this.port = port;
        this.adaptee = adaptee;
        this.requestCount = 0;
        this.isActive = true;
    }
    
    @Override
    public String getXMLData() {
        if (isActive) {
            requestCount++;
            System.out.println("   🖥️  " + getName() + " xử lý yêu cầu getXMLData() - Request #" + requestCount);
            return adaptee.getXMLData();
        }
        return null;
    }
    
    @Override
    public void processXMLData(String xmlData) {
        if (isActive) {
            requestCount++;
            System.out.println("   🖥️  " + getName() + " xử lý yêu cầu processXMLData() - Request #" + requestCount);
            adaptee.processXMLData(xmlData);
        }
    }
    
    public String getName() {
        return name + " (localhost:" + port + ")";
    }
    
    public int getRequestCount() {
        return requestCount;
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
        System.out.println("      📊 Requests: " + requestCount);
    }
}
