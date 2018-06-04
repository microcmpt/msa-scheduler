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
     * The Host.
     */
    @Value("${scheduler.mail.host}")
    private String host;

    /**
     * The User name.
     */
    @Value("${scheduler.mail.username}")
    private String username;

    /**
     * The Password.
     */
    @Value("${scheduler.mail.password}")
    private String password;

    /**
     * The Protocol.
     */
    @Value("${scheduler.mail.protocol}")
    private String protocol;

    /**
     * The To.
     */
    @Value("${scheduler.mail.to}")
    private String to;
}
