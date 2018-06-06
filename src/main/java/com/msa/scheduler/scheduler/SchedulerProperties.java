package com.msa.scheduler.scheduler;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Scheduler properties.
 *
 * @author sxp
 */
@Getter
@Configuration
@PropertySource("file:///${config.path.prefix}/config/scheduler.properties")
public class SchedulerProperties {
    /**
     * The Servers.
     */
    @Value("${scheduler.zookeeper.servers}")
    private String servers;


    /**
     * The Thread count.
     */
    @Value("${scheduler.quartz.threadPool.threadCount}")
    private String threadCount;

    /**
     * The Table prefix.
     */
    @Value("${scheduler.quartz.jobStore.tablePrefix}")
    private String tablePrefix;

    /**
     * The Driver.
     */
    @Value("${scheduler.quartz.dataSource.quartzDS.driver}")
    private String driver;

    /**
     * The Url.
     */
    @Value("${scheduler.quartz.dataSource.quartzDS.URL}")
    private String URL;

    /**
     * The User.
     */
    @Value("${scheduler.quartz.dataSource.quartzDS.user}")
    private String user;

    /**
     * The Ds password.
     */
    @Value("${scheduler.quartz.dataSource.quartzDS.password}")
    private String dsPassword;

    /**
     * The Max connections.
     */
    @Value("${scheduler.quartz.dataSource.quartzDS.maxConnections}")
    private String maxConnections;

    /**
     * The Is clustered.
     */
    @Value("${scheduler.quartz.jobStore.isClustered}")
    private String isClustered;

    /**
     * The Cluster checkin interval.
     */
    @Value("${scheduler.quartz.jobStore.clusterCheckinInterval}")
    private String clusterCheckinInterval;

}
