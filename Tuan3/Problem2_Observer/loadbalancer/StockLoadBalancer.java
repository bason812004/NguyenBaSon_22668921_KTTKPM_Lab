package loadbalancer;

import stock.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Load Balancer cho Stock notification servers
 */
public class StockLoadBalancer implements StockObserver {
    private String name;
    private List<StockServer> servers;
    private LoadBalancingStrategy strategy;
    
    public StockLoadBalancer(String name, LoadBalancingStrategy strategy) {
        this.name = name;
        this.servers = new ArrayList<>();
        this.strategy = strategy;
    }
    
    public void addServer(StockServer server) {
        servers.add(server);
    }
    
    @Override
    public void update(String stockName, double price) {
        StockServer server = strategy.selectServer(servers);
        if (server != null) {
            server.update(stockName, price);
        } else {
            System.out.println("   ⚠️  Không có server khả dụng!");
        }
    }
    
    public String getName() {
        return name + " (Load Balancer)";
    }
    
    public void displayInfo() {
        System.out.println("\n⚖️  " + getName());
        System.out.println("   Strategy: " + strategy.getClass().getSimpleName());
        System.out.println("   Servers: " + servers.size());
        System.out.println();
        for (StockServer server : servers) {
            server.displayInfo();
        }
    }
    
    public List<StockServer> getServers() {
        return servers;
    }
    
    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }
}
