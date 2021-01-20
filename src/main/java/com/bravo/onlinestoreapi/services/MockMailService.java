package com.bravo.onlinestoreapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public class MockMailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Simulando envio de email...");
        LOG.info(simpleMailMessage.toString());
        LOG.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage mimeMessage) {
        LOG.info("Simulando envio de email Html...");
        LOG.info(mimeMessage.toString());
        LOG.info("Email enviado");
    }
}
