package com.project.wab.exception;

/**
 * @author "Vladyslav Paun"
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

}
