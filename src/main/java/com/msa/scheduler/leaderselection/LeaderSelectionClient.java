package com.msa.scheduler.leaderselection;

import com.msa.api.regcovery.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Leader selection client.
 * @author sxp
 */
@Slf4j
public class LeaderSelectionClient extends LeaderSelectorListenerAdapter implements Closeable {
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Leader selector.
     */
    private final LeaderSelector leaderSelector;
    /**
     * The Registry.
     */
    private final ServiceRegistry registry;
    /**
     * The Port.
     */
    private final int port;
    /**
     * The Leader count.
     */
    private final AtomicInteger leaderCount = new AtomicInteger(1);

    /**
     * Instantiates a new Leader selection client.
     *
     * @param client   the client
     * @param registry the registry
     * @param path     the path
     * @param name     the name
     * @param port     the port
     */
    public LeaderSelectionClient(CuratorFramework client, ServiceRegistry registry, String path, String name, int port) {
        this.name = name;
        this.registry = registry;
        this.port = port;
        this.leaderSelector = new LeaderSelector(client, path, this);
        this.leaderSelector.autoRequeue();
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     * <p>
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    /**
     * Start.
     */
    public void start() {
        leaderSelector.start();
    }

    /**
     * Has leader boolean.
     *
     * @return the boolean
     */
    public boolean hasLeader() {
        return leaderSelector.hasLeadership();
    }

    /**
     * Called when your instance has been granted leadership. This method
     * should not return until you wish to release leadership
     *
     * @param client the client
     * @throws Exception any errors
     */
    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        log.info(name + " is now the leader.");
        log.info(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
        String hostAndPort = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        registry.registry("leader", hostAndPort);
        while (true) {}
    }
}
