package loadbalancer;

import java.util.List;

/**
 * Least Connection Load Balancing Strategy
 * Chọn server có ít request nhất
 */
public class LeastConnectionStrategy implements LoadBalancingStrategy {
    
    @Override
    public Server selectServer(List<Server> servers) {
        if (servers.isEmpty()) {
            return null;
        }
        
        Server selectedServer = null;
        int minRequests = Integer.MAX_VALUE;
        
        for (Server server : servers) {
            if (server.isActive() && server.getRequestCount() < minRequests) {
                minRequests = server.getRequestCount();
                selectedServer = server;
            }
        }
        
        return selectedServer;
    }
}
