package com.msa.scheduler.support.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Eureka discovery client.
 *
 * @author sxp
 */
@Component
public class EurekaDiscoveryClient {
    /**
     * The Discovery client.
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * Discovery url string.
     *
     * @param serviceId the service id
     * @return the urls
     */
    public String discoveryUrl(String serviceId) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return "";
        }
        ServiceInstance serviceInstance = serviceInstances.get(ThreadLocalRandom.current().nextInt(serviceInstances.size()));
        return serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
}
