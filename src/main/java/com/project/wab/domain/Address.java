package com.project.wab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Address1 is mandatory")
    @Column(name = "address1", nullable = false)
    private String address1;

    @Column(name = "address2")
    private String address2;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be 5 digits")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotBlank(message = "City is mandatory")
    @Column(name = "city", nullable = false)
    private String city;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "shipment_method_id")
    private ShipmentMethod shipmentMethod;
}

