package com.msa.scheduler.mail;

import com.msa.scheduler.scheduler.SchedulerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
    private SchedulerProperties properties;

    /**
     * Mail sender java mail sender.
     *
     * @return the java mail sender
     */
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyProperties(sender);
        return sender;
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
    }
}
