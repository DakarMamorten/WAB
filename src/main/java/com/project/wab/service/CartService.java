package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.Product;
import com.project.wab.domain.User;
import com.project.wab.exception.CartNotFoundException;
import com.project.wab.exception.ProductNotFoundException;
import com.project.wab.repository.CartRepository;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartServiceHelper cartServiceHelper;

    public Cart findCartByUserID(Long userID) {
        return cartRepository.findCartByUserID(userID).orElse(null);
    }

    public Cart findById(String id) {
        return cartRepository.findById(UUID.fromString(id)).orElseThrow(CartNotFoundException::new);
    }

    public String findCartIdByUserID(Long userId) {
        final Optional<UUID> cartIdByUserID = cartRepository.findCartIdByUserID(userId);
        if (cartIdByUserID.isPresent()) {
            return cartIdByUserID.get().toString();
        }
        return "";
    }

    @Transactional
    public String createAndPopulateCart(Long productId, String cartId, User currentUser) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        if (cartId != null) {
            final Cart cartFromDb = cartRepository.findById(UUID.fromString(cartId)).orElseThrow();
            cartServiceHelper.populateCart(product, cartFromDb);
            return cartId;
        } else {
            Cart cart = new Cart();
            if (currentUser == null) {
                cart.setExpireDate(LocalDateTime.now().plusHours(2));
            } else {
                cart.setUserId(currentUser.getId());
                cart.setExpireDate(null);
            }
            cartRepository.save(cart);
            cartServiceHelper.populateCart(product, cart);
            return cart.getId().toString();
        }
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
