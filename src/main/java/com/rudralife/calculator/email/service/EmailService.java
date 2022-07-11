package com.rudralife.calculator.email.service;

import com.rudralife.calculator.email.model.EmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;


@Service
public class EmailService {

    @Value("${sender.email}")
    private String senderEmail;

    @Value("${sender.password}")
    private String senderPassword;

    public EmailResponse sendEmail(String text, String recipient) throws MessagingException {
        Message message = new MimeMessage(configureSession());
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Mail Subject");
        String msg = text;
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
        return null;
    }

    private Properties getEmailProperties() {
        try(FileReader reader = new FileReader("email.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (Exception ex) {
            return null;
        }
    }

    private Session configureSession() {
        return Session.getInstance(Objects.requireNonNull(getEmailProperties()), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
}
