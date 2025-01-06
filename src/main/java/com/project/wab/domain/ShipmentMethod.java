package com.project.wab.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Table(name = "shipment_method")
public class ShipmentMethod extends ReferenceBookEntity {
}
