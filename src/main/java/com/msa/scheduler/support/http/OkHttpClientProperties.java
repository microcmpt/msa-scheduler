package com.msa.scheduler.support.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("file:///${config.path.prefix}/config/scheduler.properties")
@ConfigurationProperties(prefix = "scheduler.okhttp")
public class OkHttpClientProperties {

    private int connectTimeout = 10;
    private int readTimeout = 10;
    private int writeTimeout = 10;
    private int retries;
}
