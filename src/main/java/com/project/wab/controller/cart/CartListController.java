package com.project.wab.controller.cart;

import com.project.wab.dto.CartItemDTO;
import com.project.wab.service.CartItemService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartListController {

    private final CartItemService cartItemService;
// Check is token (if token is blank - return cart/list)
// if ok - get products and return cart/list
// if bad token return cart/list
@GetMapping("/list")
public String showCart(Model model, HttpServletRequest request) {
    String cartToken = WebUtil.checkToken(request);

    if (cartToken == null || cartToken.isBlank()) {
        model.addAttribute("cartItems", List.of());
        model.addAttribute("totalPrice", BigDecimal.ZERO);
        return "cart/list";
    }

    try {
        UUID cartId = UUID.fromString(cartToken);
        List<CartItemDTO> cartItems = cartItemService.getCartItemsDTOByCartId(cartId);
        BigDecimal totalPrice = cartItemService.calculateTotalPriceByCartId(cartId);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart/list";

    } catch (IllegalArgumentException e) {
        model.addAttribute("cartItems", List.of());
        model.addAttribute("totalPrice", BigDecimal.ZERO);
        return "cart/list";
    }
}

}

