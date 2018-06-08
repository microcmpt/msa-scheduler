package com.msa.scheduler.support.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * The type Ok http client invoker.
 */
@Slf4j
@Component
public class OkHttpClientInvoker implements Serializable {
    /**
     * The Client.
     */
    @Autowired
    private OkHttpClient client;

    /**
     * Invoke string.
     *
     * @param url the url
     * @return the string
     */
    public String invoke(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * The type Ok http client configuration.
     */
    @Configuration
    public static class OkHttpClientConfiguration {
        /**
         * Ok http client ok http client invoker . ok http client adapter.
         *
         * @param properties the properties
         * @return the ok http client invoker . ok http client adapter
         */
        @Bean
        public OkHttpClient okHttpClient(OkHttpClientProperties properties) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                    .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS)
                    .writeTimeout(properties.getWriteTimeout(), TimeUnit.SECONDS);
            return builder.build();
        }
    }
}
