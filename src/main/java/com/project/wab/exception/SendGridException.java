package com.project.wab.exception;

public class SendGridException extends RuntimeException {
    public SendGridException(String message) {
        super(message);
    }
}
