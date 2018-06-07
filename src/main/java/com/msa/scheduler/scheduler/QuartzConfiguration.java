package com.msa.scheduler.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * The type Quartz configuration.
 *
 * @author sxp
 */
@Slf4j
@Configuration
public class QuartzConfiguration {

    /**
     * The Scheduler properties.
     */
    @Autowired
    private SchedulerProperties schedulerProperties;

    /**
     * Scheduler factory bean scheduler factory bean.
     *
     * @return the scheduler factory bean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("quartz.properties"));
            applyProperties(properties);
            schedulerFactoryBean.setQuartzProperties(properties);
        } catch (IOException e) {
            log.error("load {} file error", "quartz.properties", e);
        }
        return schedulerFactoryBean;
    }

    /**
     * Apply properties.
     *
     * @param properties the properties
     */
    private void applyProperties(Properties properties) {
        // thread pool config
        if (StringUtils.hasText(schedulerProperties.getThreadCount())) {
            properties.setProperty("org.quartz.threadPool.threadCount", schedulerProperties.getThreadCount());
        }

        // db config
        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.setProperty("org.quartz.jobStore.dataSource", "quartzDS");
        Assert.hasText(schedulerProperties.getDriver(), "scheduler.quartz.dataSource.quartzDS.driver is missing");
        properties.setProperty("org.quartz.dataSource.quartzDS.driver", schedulerProperties.getDriver());
        Assert.hasText(schedulerProperties.getURL(), "scheduler.quartz.dataSource.quartzDS.URL is missing");
        properties.setProperty("org.quartz.dataSource.quartzDS.URL", schedulerProperties.getURL());
        Assert.hasText(schedulerProperties.getUser(), "scheduler.quartz.dataSource.quartzDS.user is missing");
        properties.setProperty("org.quartz.dataSource.quartzDS.user", schedulerProperties.getUser());
        Assert.hasText(schedulerProperties.getDsPassword(), "scheduler.quartz.dataSource.quartzDS.password is missing");
        properties.setProperty("org.quartz.dataSource.quartzDS.password", schedulerProperties.getDsPassword());
        if (StringUtils.hasText(schedulerProperties.getMaxConnections())) {
            properties.setProperty("org.quartz.dataSource.quartzDS.maxConnections", schedulerProperties.getMaxConnections());
        }

        // cluster config
        if (StringUtils.hasText(schedulerProperties.getIsClustered())) {
            Assert.isTrue(Boolean.valueOf(schedulerProperties.getIsClustered()), "scheduler.quartz.jobStore.isClustered is false, actual allow true");
            properties.setProperty("org.quartz.jobStore.isClustered", schedulerProperties.getIsClustered());
        }
        if (StringUtils.hasText(schedulerProperties.getClusterCheckinInterval())) {
            properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", schedulerProperties.getClusterCheckinInterval());
        }
    }

    /**
     * Scheduler scheduler.
     *
     * @param schedulerFactoryBean the scheduler factory bean
     * @return the scheduler
     */
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}
