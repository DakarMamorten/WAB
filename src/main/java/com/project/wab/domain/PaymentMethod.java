package com.project.wab.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends ReferenceBookEntity {
}
