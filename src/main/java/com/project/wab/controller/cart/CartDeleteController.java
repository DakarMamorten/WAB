package com.project.wab.controller.cart;

import com.project.wab.dto.CartItemDTO;
import com.project.wab.service.CartItemService;
import com.project.wab.utils.CartHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartDeleteController {
    private final CartItemService cartItemService;

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam Long productId,
                                 @RequestParam UUID cartId,
                                 RedirectAttributes redirectAttributes) {
        cartItemService.removeCartItem(productId, cartId);

        final List<CartItemDTO> cartItemsDTOByCartId = cartItemService.getCartItemsDTOByCartId(cartId);
        redirectAttributes.addFlashAttribute("cartItems", cartItemsDTOByCartId);
        redirectAttributes.addFlashAttribute("totalPrice", CartHelper.calculateCartTotal(cartItemsDTOByCartId));

        return "redirect:/cart/list";
    }
}

