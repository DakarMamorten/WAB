package com.project.wab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author "Vladyslav Paun"
 */
@Data
public class AddressDTO {
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Street name is mandatory")
    private String streetName;

    @NotBlank(message = "House is mandatory")
    private String house;

    private String flat;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be 5 digits")
    private String postalCode;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    private String phone;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
}


