package com.project.wab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

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

    @NotBlank(message = "Street name is mandatory")
    @Column(name = "street_name", nullable = false)
    private String streetName;

    @NotBlank(message = "House is mandatory")
    @Column(name = "house", nullable = false)
    private String house;

    @Column(name = "flat")
    private String flat;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be 5 digits")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @NotBlank(message = "City is mandatory")
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

}

