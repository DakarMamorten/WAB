package com.project.wab.domain.order;

/**
 * @author "Vladyslav Paun"
 * <p>
 * * Enum representing the payment status for an Order.
 */

public enum PaymentState {

    /**
     * Indicates that payment balance is due for the Order.
     */
    BALANCE_DUE,

    /**
     * Indicates that payment for the Order has failed.
     */
    FAILED,

    /**
     * Indicates that payment for the Order is pending.
     */
    PENDING,

    /**
     * Indicates that payment for the Order is made on a credit basis.
     */
    CREDIT_OWED,

    /**
     * Indicates that the Order is paid for.
     */
    PAID
}

