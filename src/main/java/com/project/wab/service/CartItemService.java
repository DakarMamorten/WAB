package com.project.wab.service;

import com.project.wab.domain.CartItem;
import com.project.wab.domain.Product;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem addProductToCart(Long productId, Long id, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cartItem;
    }
}
