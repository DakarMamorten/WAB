package com.project.wab.controller.cart;

import com.project.wab.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartDeleteController {
    private final CartItemService cartItemService;

    @PostMapping("/delete")
    public String delete(final Long cartItemId) {
//        cartItemService.removeCartItem(cartItemId);
        return "redirect:/cart/list";
    }
}

