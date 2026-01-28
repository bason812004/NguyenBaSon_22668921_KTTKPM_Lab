package loadbalancer;

import java.util.List;

/**
 * Strategy interface cho load balancing
 */
public interface LoadBalancingStrategy {
    <T extends ServerInfo> T selectServer(List<T> servers);
}

/**
 * Interface chung cho server info
 */
interface ServerInfo {
    boolean isActive();
    int getNotificationCount();
}
