package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.CartRepository;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

//    public Cart getCartByUserId(Long userId) {
//        return cartRepository.findByUserId(userId)
//                .orElseGet(() -> createNewCartForUser(userId));
//    }

    public Cart createCart(String token) {
        return cartRepository.findByToken(token)
                .orElseGet(() -> {
                    Cart cart = new Cart(token);
                    return cartRepository.save(cart);
                });
    }

    public void addItemToCart(String token, String productName, int quantity) {
        Cart cart = cartRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Cart not found for token: " + token));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductName(productName);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    public Cart getCartByToken(String token) {
        return cartRepository.findByToken(token)
                .orElseThrow();
    }


    private Cart createNewCartForUser(Long userId) {
        Cart cart = new Cart();
//        cart.setUser(userRepository.findById(userId).orElseThrow());
        return cartRepository.save(cart);
    }

}
