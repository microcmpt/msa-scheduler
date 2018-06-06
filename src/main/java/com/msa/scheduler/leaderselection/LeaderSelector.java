package com.msa.scheduler.leaderselection;

import com.msa.api.regcovery.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The type Leader selector.
 *
 * @author sxp
 */
@Slf4j
public class LeaderSelector implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private static final String PATH = "/leader";
    private int port;
    @Value("${scheduler.zookeeper.servers}")
    private String connectString;
    private CuratorFramework client;
    private LeaderSelectionClient leaderSelectionClient;
    @Autowired
    private ServiceRegistry registry;

    /**
     * Select.
     */
    private void select() {
        client = CuratorFrameworkFactory.newClient(connectString,
                new ExponentialBackoffRetry(1000, 3));
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("found hostname exception", e);
        }
        leaderSelectionClient = new LeaderSelectionClient(client, registry, PATH, "Client:" + hostname, port);
        client.start();
        leaderSelectionClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down leader selection client...");
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(leaderSelectionClient);
        }));
    }

    /**
     * Has leader boolean.
     *
     * @return the boolean
     */
    public boolean hasLeader() {
        return leaderSelectionClient.hasLeader();
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.port = event.getApplicationContext().getEmbeddedServletContainer().getPort();
        select();
    }
}
