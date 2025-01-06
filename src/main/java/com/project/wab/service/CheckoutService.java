package com.project.wab.service;

import com.project.wab.utils.CartHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Component
@RequiredArgsConstructor
public class CheckoutService {
    private final CartService cartService;
    private final AddressService addressService;

    public void preView(String cartId, Model model) {
        var addressId = (Long) model.getAttribute("addressId");
        model.addAttribute("addressId", addressId);
        var cartItemsDto = cartService.getCartItemsDto(UUID.fromString(cartId));
        model.addAttribute("cartItems", cartItemsDto);
        model.addAttribute("cartTotal", CartHelper.calculateCartTotal(cartItemsDto));
        var address = addressService.findById(addressId);
        model.addAttribute("addressDTO", addressService.convertToDTO(address));
    }
}
