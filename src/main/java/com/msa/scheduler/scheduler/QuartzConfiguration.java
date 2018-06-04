package com.msa.scheduler.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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
     * The constant configPathSuffix.
     */
    private static final String configPathSuffix = "config/quartz.properties";
    /**
     * The Config path prefix.
     */
    @Value("${config.path.prefix}")
    private String configPathPrefix;

    /**
     * Scheduler factory bean scheduler factory bean.
     *
     * @return the scheduler factory bean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        String configPath = "";
        try {
            if (configPathPrefix.lastIndexOf("/") != -1) {
                configPath = configPathPrefix + configPathSuffix;
            } else {
                configPath = configPathPrefix + "/" + configPathSuffix;
            }
            Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(configPath));
            schedulerFactoryBean.setQuartzProperties(properties);
        } catch (IOException e) {
            log.error("load {} file error", configPath, e);
        }
        return schedulerFactoryBean;
    }

    /**
     * Scheduler scheduler.
     *
     * @return the scheduler
     */
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getScheduler();
    }
}
