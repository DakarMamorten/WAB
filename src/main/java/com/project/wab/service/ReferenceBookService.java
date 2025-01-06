package com.project.wab.service;

import com.project.wab.dto.CartItemDTO;
import com.project.wab.utils.CartHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class ReferenceBookService {
    private final ShipmentMethodService shipmentMethodService;
    private final PaymentMethodService paymentMethodService;
    private final CartService cartService;

    public void getReferenceBookForCheckout(Model model, String token) {
        model.addAttribute("shipmentMethod", shipmentMethodService.findAllDto());
        model.addAttribute("paymentMethod", paymentMethodService.findAllDto());
        List<CartItemDTO> cartItemsDto = cartService.getCartItemsDto(UUID.fromString(token));
        model.addAttribute("cartItems", cartItemsDto);
        model.addAttribute("cartTotal", CartHelper.calculateCartTotal(cartItemsDto));
    }
}
