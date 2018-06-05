package com.msa.scheduler.dal;

import com.msa.scheduler.scheduler.SchedulerProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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

    /**
     * The type Data source initializer configuration.
     */
    @Configuration
    public static class DataSourceInitializerConfiguration {
        /**
         * Data source initializer data source initializer.
         *
         * @param properties the properties
         * @return the data source initializer
         */
        @Bean
        public DataSourceInitializer dataSourceInitializer(SchedulerProperties properties) {
            DataSourceInitializer initializer = new DataSourceInitializer();
            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName(properties.getDriver())
                    .url(properties.getURL())
                    .username(properties.getUser())
                    .password(properties.getDsPassword())
                    .build();
            initializer.setDataSource(dataSource);
            ClassPathResource sqlResource = new ClassPathResource("quartz.sql");
            initializer.setDatabasePopulator(new ResourceDatabasePopulator(false,
                    false, "utf-8", sqlResource));
            return initializer;
        }
    }
}
