package com.msa.scheduler.leaderselection;

import com.msa.api.regcovery.discovery.ZkServiceDiscovery;
import com.msa.api.regcovery.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Leader selector configuration.
 *
 * @author sxp
 */
@Configuration
public class LeaderSelectorConfiguration {
    @Value("${scheduler.zookeeper.servers}")
    private String connectString;

    /**
     * Selector leader selector.
     *
     * @return the leader selector
     */
    @Bean(initMethod = "select")
    public LeaderSelector selector() {
        return new LeaderSelector();
    }

    /**
     * Registry service registry.
     *
     * @return the service registry
     */
    @Bean
    public ServiceRegistry registry() {
        ServiceRegistry registry = new ServiceRegistry();
        registry.setZkAddress(connectString);
        return registry;
    }

    /**
     * Discovery zk service discovery.
     *
     * @return the zk service discovery
     */
    @Bean
    public ZkServiceDiscovery discovery() {
        ZkServiceDiscovery zkServiceDiscovery = new ZkServiceDiscovery();
        zkServiceDiscovery.setZkAddress(connectString);
        return zkServiceDiscovery;
    }
}
