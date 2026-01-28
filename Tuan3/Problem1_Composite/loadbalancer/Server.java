package loadbalancer;

import filesystem.FileSystemComponent;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Đại diện cho một server xử lý yêu cầu
 */
public class Server implements FileSystemComponent {
    private String name;
    private int port;
    private FileSystemComponent fileSystem;
    private AtomicInteger requestCount;
    private boolean isActive;
    
    public Server(String name, int port, FileSystemComponent fileSystem) {
        this.name = name;
        this.port = port;
        this.fileSystem = fileSystem;
        this.requestCount = new AtomicInteger(0);
        this.isActive = true;
    }
    
    @Override
    public String getName() {
        return name + " (localhost:" + port + ")";
    }
    
    @Override
    public long getSize() {
        return fileSystem.getSize();
    }
    
    @Override
    public void display(String indent) {
        String status = isActive ? "🟢 ACTIVE" : "🔴 INACTIVE";
        System.out.println(indent + "🖥️  " + getName() + " - " + status);
        System.out.println(indent + "   📊 Requests: " + requestCount.get());
        System.out.println(indent + "   💾 Data: " + getSize() + " bytes");
        fileSystem.display(indent + "   ");
    }
    
    public void handleRequest() {
        requestCount.incrementAndGet();
        System.out.println("   ➜ " + getName() + " đang xử lý yêu cầu #" + requestCount.get());
    }
    
    public int getRequestCount() {
        return requestCount.get();
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
}
