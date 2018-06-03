package com.msa.scheduler.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author sxp
 */
@Configuration
@PropertySource("file:///${config.path.prefix}/config/scheduler.properties")
public class SchedulerProperties {
}
