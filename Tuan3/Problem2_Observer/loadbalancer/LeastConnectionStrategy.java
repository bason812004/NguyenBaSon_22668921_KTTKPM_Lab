package loadbalancer;

import java.util.List;

/**
 * Least Connection Strategy
 */
public class LeastConnectionStrategy implements LoadBalancingStrategy {
    
    @Override
    public <T extends ServerInfo> T selectServer(List<T> servers) {
        if (servers.isEmpty()) return null;
        
        T selected = null;
        int minCount = Integer.MAX_VALUE;
        
        for (T server : servers) {
            if (server.isActive() && server.getNotificationCount() < minCount) {
                minCount = server.getNotificationCount();
                selected = server;
            }
        }
        return selected;
    }
}
