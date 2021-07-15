package com.synrgybootcamp.project.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("support@beasyapp.tech");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            mailSender.send(message);
        }
        catch (MessagingException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Failed to send email");
        }
    }
}
