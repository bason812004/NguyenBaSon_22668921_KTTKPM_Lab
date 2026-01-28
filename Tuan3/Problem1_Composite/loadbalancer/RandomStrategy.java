package loadbalancer;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Random Load Balancing Strategy
 * Chọn server ngẫu nhiên
 */
public class RandomStrategy implements LoadBalancingStrategy {
    private Random random = new Random();
    
    @Override
    public Server selectServer(List<Server> servers) {
        if (servers.isEmpty()) {
            return null;
        }
        
        // Lọc các server active
        List<Server> activeServers = servers.stream()
                .filter(Server::isActive)
                .collect(Collectors.toList());
        
        if (activeServers.isEmpty()) {
            return null;
        }
        
        int index = random.nextInt(activeServers.size());
        return activeServers.get(index);
    }
}
