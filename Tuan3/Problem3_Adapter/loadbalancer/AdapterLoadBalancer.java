package loadbalancer;

import adapter.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Load Balancer cho Adapter servers
 */
public class AdapterLoadBalancer implements XMLService {
    private String name;
    private List<AdapterServer> servers;
    private int currentIndex;
    
    public AdapterLoadBalancer(String name) {
        this.name = name;
        this.servers = new ArrayList<>();
        this.currentIndex = 0;
    }
    
    public void addServer(AdapterServer server) {
        servers.add(server);
    }
    
    @Override
    public String getXMLData() {
        AdapterServer server = selectServer();
        if (server != null) {
            return server.getXMLData();
        }
        System.out.println("   ⚠️  Không có server khả dụng!");
        return null;
    }
    
    @Override
    public void processXMLData(String xmlData) {
        AdapterServer server = selectServer();
        if (server != null) {
            server.processXMLData(xmlData);
        } else {
            System.out.println("   ⚠️  Không có server khả dụng!");
        }
    }
    
    private AdapterServer selectServer() {
        if (servers.isEmpty()) return null;
        
        int attempts = 0;
        while (attempts < servers.size()) {
            AdapterServer server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size();
            
            if (server.isActive()) {
                return server;
            }
            attempts++;
        }
        return null;
    }
    
    public String getName() {
        return name + " (Load Balancer)";
    }
    
    public void displayInfo() {
        System.out.println("\n⚖️  " + getName());
        System.out.println("   Servers: " + servers.size());
        System.out.println();
        for (AdapterServer server : servers) {
            server.displayInfo();
        }
    }
    
    public List<AdapterServer> getServers() {
        return servers;
    }
}
