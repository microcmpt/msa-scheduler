package com.msa.scheduler.http;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Ok http configuration.
 * @author sxp
 */
@Configuration
public class OkHttpConfiguration {

    /**
     * Ok http client ok http client.
     *
     * @return the ok http client
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
