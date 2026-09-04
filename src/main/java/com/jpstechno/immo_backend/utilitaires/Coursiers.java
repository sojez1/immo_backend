package com.jpstechno.immo_backend.utilitaires;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jpstechno.immo_backend.services.CoursierService;

import jakarta.mail.internet.MimeMessage;

@Service
public class Coursiers implements CoursierService {

    private final JavaMailSender javaMailSender;

    public Coursiers(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;

    }

    @Override
    public void envoyerMail(String objetDuMessage, String corpsMessage, String emailDestinataire) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailDestinataire);
            helper.setText(corpsMessage, true);
            helper.setSubject(objetDuMessage);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Echec lors de l'envoi du message de validation de email\n" + e.getMessage());
        }

    }

}
