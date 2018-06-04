package com.msa.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SendMailTest {

    @Autowired
    JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String name;

    @Test
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(name);
        message.setTo(name);
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        sender.send(message);
    }
}
