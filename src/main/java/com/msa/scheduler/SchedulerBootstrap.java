package com.msa.scheduler;

import com.msa.scheduler.leaderselection.LeaderSelector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * The type Scheduler bootstrap.
 */
@SpringBootApplication
public class SchedulerBootstrap {

    /**
     * Selector leader selector.
     *
     * @return the leader selector
     */
    @Bean(initMethod = "select")
    public LeaderSelector selector() {
        return new LeaderSelector();
    }

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
