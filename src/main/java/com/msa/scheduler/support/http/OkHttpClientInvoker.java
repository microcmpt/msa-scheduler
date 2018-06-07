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
    private OkHttpClientAdapter client;

    /**
     * Invoke string.
     *
     * @param url the url
     * @return the string
     */
    public String invoke(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            log.error("send request url:{} exception", url);
        }
        return null;
    }

    /**
     * The type Ok http client configuration.
     */
    @Configuration
    public class OkHttpClientConfiguration {
        /**
         * Ok http client ok http client invoker . ok http client adapter.
         *
         * @return the ok http client invoker . ok http client adapter
         */
        @Bean
        public OkHttpClientInvoker.OkHttpClientAdapter okHttpClient() {
            return new OkHttpClientInvoker.OkHttpClientAdapter();
        }
    }

    /**
     * The type Ok http client adapter.
     */
    static class OkHttpClientAdapter extends OkHttpClient implements Serializable {
        /**
         * Instantiates a new Ok http client adapter.
         */
        public OkHttpClientAdapter() {
        }
    }
}
