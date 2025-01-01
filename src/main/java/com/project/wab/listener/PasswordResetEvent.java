package com.project.wab.listener;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author "Vladyslav Paun"
 */
@Getter
public class PasswordResetEvent extends ApplicationEvent {

    private final String email;
    private final String token;
    private final String name;

    public PasswordResetEvent(Object source, String email, String token, String name) {
        super(source);
        this.email = email;
        this.token = token;
        this.name = name;
    }
}
