package com.msa.scheduler.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The type Ok http client invoker.
 */
@Slf4j
@Component
public class OkHttpClientInvoker {
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
}
