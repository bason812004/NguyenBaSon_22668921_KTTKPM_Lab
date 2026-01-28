package loadbalancer;

import java.util.List;

/**
 * Strategy interface cho các thuật toán load balancing
 */
public interface LoadBalancingStrategy {
    Server selectServer(List<Server> servers);
}
