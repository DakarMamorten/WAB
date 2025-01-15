package com.project.wab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Data
public class AddressDTO {
    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Address1 is mandatory")
    private String address1;

    private String address2;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code must be 5 digits")
    private String postalCode;

    @NotBlank(message = "City is mandatory")
    private String city;

    private Boolean billingAddress;
    private Boolean saveInfo;

    private Long paymentMethodId;
    private String paymentMethod;
    private Long shippingMethodId;
    private String shippingMethod;

    private Long totalCartItems;
    private BigDecimal totalCart;
    private List<CartItemDTO> cartItems;
}


