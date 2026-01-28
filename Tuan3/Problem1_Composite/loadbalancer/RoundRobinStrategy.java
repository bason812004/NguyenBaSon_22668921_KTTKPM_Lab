package loadbalancer;

import java.util.List;

/**
 * Round Robin Load Balancing Strategy
 * Phân phối yêu cầu theo vòng tròn
 */
public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;
    
    @Override
    public Server selectServer(List<Server> servers) {
        if (servers.isEmpty()) {
            return null;
        }
        
        // Tìm server active tiếp theo
        int attempts = 0;
        while (attempts < servers.size()) {
            Server server = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % servers.size();
            
            if (server.isActive()) {
                return server;
            }
            attempts++;
        }
        
        return null; // Không có server active
    }
}
