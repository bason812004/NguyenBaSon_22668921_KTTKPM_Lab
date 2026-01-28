package loadbalancer;

import filesystem.FileSystemComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite cho Load Balancer
 * Quản lý nhiều servers và phân phối tải
 */
public class LoadBalancer implements FileSystemComponent {
    private String name;
    private List<Server> servers;
    private int currentIndex;
    private LoadBalancingStrategy strategy;
    
    public LoadBalancer(String name, LoadBalancingStrategy strategy) {
        this.name = name;
        this.servers = new ArrayList<>();
        this.currentIndex = 0;
        this.strategy = strategy;
    }
    
    public void addServer(Server server) {
        servers.add(server);
    }
    
    public void removeServer(Server server) {
        servers.remove(server);
    }
    
    @Override
    public String getName() {
        return name + " (Load Balancer)";
    }
    
    @Override
    public long getSize() {
        long totalSize = 0;
        for (Server server : servers) {
            totalSize += server.getSize();
        }
        return totalSize;
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "⚖️  " + getName());
        System.out.println(indent + "   Strategy: " + strategy.getClass().getSimpleName());
        System.out.println(indent + "   Servers: " + servers.size());
        System.out.println(indent + "   Total Data: " + getSize() + " bytes");
        System.out.println(indent);
        for (Server server : servers) {
            server.display(indent + "   ");
            System.out.println(indent);
        }
    }
    
    /**
     * Phân phối yêu cầu theo chiến lược
     */
    public void distributeRequest() {
        Server server = strategy.selectServer(servers);
        if (server != null) {
            server.handleRequest();
        } else {
            System.out.println("   ⚠️  Không có server khả dụng!");
        }
    }
    
    /**
     * Phân phối nhiều yêu cầu
     */
    public void distributeRequests(int count) {
        System.out.println("\n🔄 Phân phối " + count + " yêu cầu đến các servers...\n");
        for (int i = 0; i < count; i++) {
            System.out.println("📨 Yêu cầu #" + (i + 1) + ":");
            distributeRequest();
        }
    }
    
    public List<Server> getServers() {
        return servers;
    }
    
    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }
}
