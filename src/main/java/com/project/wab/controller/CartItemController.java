package com.project.wab.controller;

import com.project.wab.domain.CartItem;
import com.project.wab.domain.User;
import com.project.wab.service.CartItemService;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/cart/items")
@RequiredArgsConstructor
public class CartItemController {
    private final UserService userService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestParam int quantity) {
        CartItem updatedItem = cartItemService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        User user = userService.findById(userId);
        List<CartItem> cartItems = cartService.getCartItems(user);
        return ResponseEntity.ok(cartItems);
    }

}
