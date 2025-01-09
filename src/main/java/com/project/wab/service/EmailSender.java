package com.project.wab.service;

import com.project.wab.dto.Email;
import com.project.wab.exception.SendGridException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * @author "Vladyslav Paun"
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailSender {
    public static final String ADMIN_EMAIL = "admin@wab.project.com";
    private final TemplateEngine htmlTemplateEngine;
    private final SendGrid sendGrid;

    public void send(Email email) {
        log.debug("Starting prepare message for send to recipient: {}...", email.getRecipientEmail());
        com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email(ADMIN_EMAIL);
        String subject = email.getSubject();
        com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(email.getRecipientEmail());
        final Map<String, Object> templateData = email.getTemplateData();
        final Context context = new Context(Locale.of("UTF-16"));
        for (Map.Entry<String, Object> entry : templateData.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        final String htmlContent = this.htmlTemplateEngine.process(email.getTemplate(), context);
        final Content content = new Content("text/html", htmlContent);
        final Mail mail = new Mail(from, subject, to, content);


        final Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            request.setBody(mail.build());
            final Response response = this.sendGrid.api(request);
            log.debug("response code: {}", response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS.value()) {
                throw new SendGridException("Daily limit of messages is reached!");
            }
            if (response.getStatusCode() == HttpStatus.ACCEPTED.value()) {
                log.info("Email has been sent to recipient: {}", email.getRecipientEmail());
            }
        } catch (IOException ex) {
            log.error("error: {}", ex.getMessage());
            throw new SendGridException(ex.getMessage());
        }
    }
}
