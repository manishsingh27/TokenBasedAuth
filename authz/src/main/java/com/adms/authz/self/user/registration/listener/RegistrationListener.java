package com.adms.authz.self.user.registration.listener;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.registration.OnRegistrationCompleteEvent;
import com.adms.authz.self.user.service.UsersService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UsersService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;
    
    final static String subject = "Registration Confirmation";

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final Users user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final Users user, final String token) {
        final String recipientAddress = user.getEmail();
        
        final String confirmationUrl = event.getAppUrl() + "/uaa/v1/api/user/registrationConfirm?token=" + token;
        final String message = "You have been successfully registered. Please click below link to activate your xplusyz account.";
        		//messages.getMessage("message.regSucc", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setReplyTo(env.getProperty("support.email"));
        email.setFrom(env.getProperty("support.from"));
        return email;
    }
}