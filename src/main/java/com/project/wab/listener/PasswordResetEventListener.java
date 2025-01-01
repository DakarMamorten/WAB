package com.project.wab.listener;

import com.project.wab.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetEventListener {

    private final EmailSender emailService;

    @Async
    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) {
        emailService.send(event);
        log.info("Email sent asynchronously to: {}", event.getEmail());
    }
}
