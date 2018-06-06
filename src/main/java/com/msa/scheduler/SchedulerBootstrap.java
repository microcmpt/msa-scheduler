package com.msa.scheduler;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * The type Scheduler bootstrap.
 */
@EnableZuulProxy
@SpringBootApplication
public class SchedulerBootstrap {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder();
        appBuilder.sources(SchedulerBootstrap.class);
        appBuilder.run(args);
    }
}
