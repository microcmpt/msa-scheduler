package com.msa.scheduler.web;

import com.msa.api.regcovery.discovery.ServiceDiscovery;
import com.msa.scheduler.http.OkHttpClientInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebProxy {
    @Autowired
    private ServiceDiscovery serviceDiscovery;
    /**
     * The Invoker.
     */
    @Autowired
    private OkHttpClientInvoker invoker;

    public void proxy(String URI) {


    }
}
