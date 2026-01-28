package loadbalancer;

import java.util.List;

/**
 * Round Robin Strategy
 */
public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;
    
    @Override
    public <T extends ServerInfo> T selectServer(List<T> servers) {
        if (servers.isEmpty()) return null;
        
        int attempts = 0;
        while (attempts < servers.size()) {
            T server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size();
            if (server.isActive()) return server;
            attempts++;
        }
        return null;
    }
}
