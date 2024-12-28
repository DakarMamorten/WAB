package com.project.wab.controller.cart;

import com.project.wab.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartUpdateController {
    private final CartItemService cartItemService;
    @PostMapping("/update")
    public String updateCartItem(Long productId,
                                 UUID cartId,
                                 int quantity,
                                 String action,
                                 RedirectAttributes redirectAttributes) {
        if ("increase".equals(action)) {
            quantity++;
        } else if ("decrease".equals(action) && quantity > 1) {
            quantity--;
        }

        cartItemService.updateCartItemQuantity(productId, cartId, quantity);

        redirectAttributes.addFlashAttribute("cartItems", cartItemService.getCartItemsDTOByCartId(cartId));
        redirectAttributes.addFlashAttribute("totalPrice", cartItemService.calculateTotalPriceByCartId(cartId));

        return "redirect:/cart/list";
    }
}
