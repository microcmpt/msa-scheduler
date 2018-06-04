package com.msa.scheduler.leaderselection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Leader selector configuration.
 * @author sxp
 */
@Configuration
public class LeaderSelectorConfiguration {
    /**
     * Selector leader selector.
     *
     * @return the leader selector
     */
    @Bean(initMethod = "select")
    public LeaderSelector selector() {
        return new LeaderSelector();
    }
}
