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
     * The Data source.
     */
    @Value("${scheduler.quartz.jobStore.dataSource}")
    private String dataSource;

    /**
     * The Driver.
     */
    @Value("${scheduler.quartz.dataSource.myDS.driver}")
    private String driver;

    /**
     * The Url.
     */
    @Value("${scheduler.quartz.dataSource.myDS.URL}")
    private String URL;

    /**
     * The User.
     */
    @Value("${scheduler.quartz.dataSource.myDS.user}")
    private String user;

    /**
     * The Ds password.
     */
    @Value("${scheduler.quartz.dataSource.myDS.password}")
    private String dsPassword;

    /**
     * The Max connections.
     */
    @Value("${scheduler.quartz.dataSource.myDS.maxConnections}")
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
