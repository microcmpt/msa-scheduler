package com.msa.scheduler.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author sxp
 */
@Component
@ConditionalOnProperty(name = "scheduler.mail.enable", havingValue = "true")
public class NotifyEmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailSettingsProperties properties;

    public void sendMail() {
        Assert.hasText(properties.getTo(), "scheduler.mail.to must be required");
        String[] tos = properties.getTo().split(",");
        Arrays.asList(tos).forEach(to -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(properties.getUsername());
            message.setTo(to);
            message.setSubject("主题：定时任务调度中心任务执行报告");
            message.setText("测试邮件内容");
            mailSender.send(message);
        });
    }
}
