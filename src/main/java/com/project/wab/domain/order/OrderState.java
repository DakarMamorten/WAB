package com.project.wab.domain.order;

/**
 * @author "Vladyslav Paun"
 * <p>
 * * Enum representing the state of the Order.
 */

public enum OrderState {

    /**
     * The default state of a new Order.
     */
    OPEN,

    /**
     * Indicates that the Order is accepted and being processed.
     */
    CONFIRMED,

    /**
     * Indicates that the Order is fulfilled.
     */
    COMPLETE,

    /**
     * Indicates that the Order is canceled.
     */
    CANCELLED

}

