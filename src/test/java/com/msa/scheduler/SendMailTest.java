package com.msa.scheduler;

import com.msa.scheduler.mail.MailSettingsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type Send mail test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SendMailTest {

    /**
     * The Sender.
     */
    @Autowired
    JavaMailSender sender;

    /**
     * The Properties.
     */
    @Autowired
    private MailSettingsProperties properties;

    /**
     * Send email.
     */
    @Test
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getUsername());
        message.setTo(properties.getTo());
        message.setSubject("主题：定时任务调度中心任务执行情况");
        message.setText("测试邮件内容");
        sender.send(message);
    }
}
