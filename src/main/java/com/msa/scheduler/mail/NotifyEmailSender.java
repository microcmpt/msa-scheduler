package com.msa.scheduler.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author sxp
 */
@Component
public class NotifyEmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail() {

    }
}
