package com.project.wab.listener;

import com.project.wab.dto.Email;
import com.project.wab.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author "Vladyslav Paun"
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetEventListener {

    private final EmailSender emailService;

    @Async
    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) {
        var email = new Email();
        email.setRecipientEmail(event.getEmail());
        email.setTemplate("/email/forgot-password-template");
        email.getTemplateData().put("token", event.getToken());
        email.setSubject("Reset password for user");

        emailService.send(email);
    }
}
