package com.msa.scheduler.support.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * The type Notify email sender.
 *
 * @author sxp
 */
public class NotifyEmailSender {
    /**
     * The Mail sender.
     */
    @Autowired
    private JavaMailSender mailSender;
    /**
     * The Properties.
     */
    @Autowired
    private MailSettingsProperties properties;

    /**
     * Send mail.
     *
     * @param content the content
     */
    public void sendMail(String content) {
        Assert.hasText(properties.getTo(), "scheduler.mail.to must be required");
        String[] tos = properties.getTo().split(",");
        Arrays.asList(tos).forEach(to -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(properties.getUsername());
            message.setTo(to);
            message.setSubject("主题：定时任务调度中心任务执行报告");
            message.setText(content);
            mailSender.send(message);
        });
    }
}
