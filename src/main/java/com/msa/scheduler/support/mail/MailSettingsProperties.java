package com.msa.scheduler.support.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Mail settings properties.
 */
@Setter
@Getter
@Configuration
@PropertySource("file:///${config.path.prefix}/config/scheduler.properties")
@ConfigurationProperties(prefix = "scheduler.mail")
public class MailSettingsProperties {
    /**
     * The Host.
     */
    private String host;

    /**
     * The User name.
     */
    private String username;

    /**
     * The Password.
     */
    private String password;

    /**
     * The Protocol.
     */
    private String protocol;

    /**
     * The To.
     */
    private String to;

    /**
     * The Properties.
     */
    private Map<String, String> properties = new HashMap<>();
}
