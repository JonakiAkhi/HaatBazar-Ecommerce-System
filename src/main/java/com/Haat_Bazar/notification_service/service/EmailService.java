package com.Haat_Bazar.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * EmailService — responsible for actually sending emails via Gmail SMTP.
 *
 * Key concepts for a beginner:
 *
 * 1. JavaMailSender is provided by spring-boot-starter-mail. Spring
 *    auto-configures it using the spring.mail.* properties you set in
 *    application.properties.
 *
 * 2. SimpleMailMessage is a plain-text email (no HTML, no attachments).
 *    For HTML emails you'd use MimeMessage instead.
 *
 * 3. @Async makes this method run on a separate thread. That means the
 *    controller can return "Notification queued" immediately, while the
 *    email sends in the background. If the email takes 3 seconds, the
 *    user doesn't have to wait 3 seconds for a response.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a plain-text email.
     *
     * @Async means this runs on a background thread (configured in AsyncConfig).
     *
     * @param to      recipient email address
     * @param subject email subject line
     * @param body    email body text
     */
    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        // "From" is automatically set to spring.mail.username

        mailSender.send(message);
        System.out.println("Email sent successfully to: " + to);
    }
}
