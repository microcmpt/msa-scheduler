package com.msa.scheduler.support.eureka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Eureka settings properties.
 *
 * @author sxp
 */
@Setter
@Getter
@Configuration
@PropertySource("file:///${config.path.prefix}/config/scheduler.properties")
@ConfigurationProperties(prefix = "scheduler.eureka.client")
public class EurekaSettingsProperties {
    /**
     * The Enable.
     */
    private boolean enable;
    /**
     * The Default zone.
     */
    private String defaultZone;
}
