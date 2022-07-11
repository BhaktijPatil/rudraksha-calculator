package com.rudralife.calculator.email.controller;

import com.rudralife.calculator.email.model.EmailResponse;
import com.rudralife.calculator.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping(value = "/send/{text}/{recipient}")
    public EmailResponse sendNotificationEmail(@PathVariable("text") String text, @PathVariable("recipient") String recipient) throws MessagingException {
        return emailService.sendEmail(text, recipient);
    }
}