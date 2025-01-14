package com.project.wab.exception;

/**
 * @author "Vladyslav Paun"
 */
public class UserAccountLockedException extends RuntimeException {
    public UserAccountLockedException(String message) {
        super(message);
    }

}
