package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.entities.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
