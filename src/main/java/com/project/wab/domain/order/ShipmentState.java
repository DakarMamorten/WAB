package com.project.wab.domain.order;

/**
 * @author "Vladyslav Paun"
 * <p>
 * * Enum representing the shipment status of the Order.
 */

public enum ShipmentState {

    /**
     * Indicates that the Order is shipped.
     */
    SHIPPED,

    /**
     * Indicates that the Order is delivered.
     */
    DELIVERED,

    /**
     * Indicates that the Order is ready to be shipped.
     */
    READY,

    /**
     * Indicates that the shipment of the Order is pending.
     */
    PENDING,
    /**
     * Indicates that the shipment of the Order is delayed.
     */
    DELAYED,
    /**
     * Indicates that items in the Order are shipped in more than one shipment.
     */
    PARTIAL,
    /**
     * Indicates that items in the Order are not in stock and will be delivered once the items are restocked.
     */
    BACKORDER,
    /**
     * Indicates that the shipment of the Order is canceled.
     */
    CANCELED
}

