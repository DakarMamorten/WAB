package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.User;
import com.project.wab.repository.CartRepository;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemService cartItemService;

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCartForUser(userId));
    }

    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cartItemService.addProductToCart(userId, productId, quantity);
        return cart;
    }

    public void checkout(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart createNewCartForUser(Long userId) {
        Cart cart = new Cart();
        cart.setUser(userRepository.findById(userId).orElseThrow());
        return cartRepository.save(cart);
    }

    public List<CartItem> getCartItems(User user) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found for user"));
        return cartItemService.getCartItemsByCartId(cart.getId());
    }
}
