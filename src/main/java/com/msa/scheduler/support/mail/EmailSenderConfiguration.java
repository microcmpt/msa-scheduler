package com.msa.scheduler.support.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

/**
 * The type Email sender configuration.
 *
 * @author sxp
 */
@Configuration
public class EmailSenderConfiguration {

    /**
     * The Properties.
     */
    @Autowired
    private MailSettingsProperties properties;

    /**
     * Mail sender java mail sender.
     *
     * @return the java mail sender
     */
    @Bean
    @ConditionalOnProperty(name = "scheduler.mail.enable", havingValue = "true")
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyProperties(sender);
        return sender;
    }

    /**
     * Notify email sender notify email sender.
     *
     * @return the notify email sender
     */
    @Bean
    @ConditionalOnProperty(name = "scheduler.mail.enable", havingValue = "true")
    public NotifyEmailSender notifyEmailSender() {
        return new NotifyEmailSender();
    }

    /**
     * Apply properties.
     *
     * @param sender the sender
     */
    private void applyProperties(JavaMailSenderImpl sender) {
        Assert.hasText(properties.getHost(), "scheduler.mail.host must be required");
        sender.setHost(this.properties.getHost());
        Assert.hasText(this.properties.getUsername(), "scheduler.mail.username must be required");
        sender.setUsername(this.properties.getUsername());
        Assert.hasText(this.properties.getPassword(), "scheduler.mail.password must be required");
        sender.setPassword(this.properties.getPassword());
        if (StringUtils.isEmpty(this.properties.getProtocol())) {
            sender.setProtocol("smtp");
        } else {
            sender.setProtocol(this.properties.getProtocol());
        }
        sender.setDefaultEncoding("UTF-8");
        if (!this.properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(this.properties.getProperties()));
        }
    }

    /**
     * As properties properties.
     *
     * @param source the source
     * @return the properties
     */
    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }
}
