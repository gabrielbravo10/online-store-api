package com.bravo.onlinestoreapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmptEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmptEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("enviando email...");
        mailSender.send(simpleMailMessage);
        LOG.info("Email enviado");
    }
}
