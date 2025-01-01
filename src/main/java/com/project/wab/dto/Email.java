package com.project.wab.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Email implements Serializable {

    private String recipientEmail;
    private String subject;
    private String template;
    private Map<String, String> templateData = new HashMap<>();

    public Email(final String recipientEmail,
                 final String subject,
                 final String template,
                 final Map<String, String> templateData) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.template = template;
        this.templateData = templateData;
    }
}
