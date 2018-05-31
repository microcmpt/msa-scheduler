package com.msa.scheduler.leaderselection;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The type Leader selector.
 */
@Slf4j
public class LeaderSelector {
    /**
     * The constant PATH.
     */
    private static final String PATH = "/leader";

    /**
     * The Connect string.
     */
    @Value("${scheduler.zookeeper.servers:localhost:2181}")
    private String connectString;

    /**
     * Select.
     */
    public void select() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString,
                new ExponentialBackoffRetry(1000, 3));
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("found hostname exception", e);
        }
        LeaderSelectionClient leaderSelectionClient = new LeaderSelectionClient(client, PATH, "Client:" + hostname);
        client.start();
        leaderSelectionClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down leader selection client...");
            CloseableUtils.closeQuietly(client);
            CloseableUtils.closeQuietly(leaderSelectionClient);
        }));
    }
}
