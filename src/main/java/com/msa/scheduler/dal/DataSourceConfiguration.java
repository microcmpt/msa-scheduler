package com.msa.scheduler.dal;

import com.msa.scheduler.scheduler.SchedulerProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * The type Data source configuration.
 *
 * @author sxp
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * Data source data source.
     *
     * @param properties the properties
     * @return the data source
     */
    @Bean
    public DataSource dataSource(SchedulerProperties properties) {
        return DataSourceBuilder.create()
                .driverClassName(properties.getDriver())
                .url(properties.getURL())
                .username(properties.getUser())
                .password(properties.getDsPassword())
                .build();
    }
}
