package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import com.project.wab.domain.Product;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.CartRepository;
import com.project.wab.repository.ProductRepository;
import com.project.wab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Cart findCartByUserID(Long userID) {
        return cartRepository.findCartByUserID(userID);
    }

    public String createAndPopulateCart(Long productId, String cartId) {
        //todo create ProductNotFoundException
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);

        if (cartId != null) {
            final Cart cartFromDb = cartRepository.findById(UUID.fromString(cartId)).orElseThrow();
            populateCart(product, cartFromDb);
            cartRepository.save(cartFromDb);
            return cartId;
        } else {
            Cart cart = new Cart();
            cart.setExpireDate(LocalDateTime.now().plusHours(2));
            cartRepository.save(cart);
            populateCart(product, cart);

            return cart.getId().toString();
        }
    }

    private void populateCart(Product product, Cart cart) {
        final CartItem item = cartItemRepository.findCartItemByCartIdAndProductId(cart.getId(), product.getId()).orElseGet(
                () -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(1);

                    CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
                    cartItem.setId(cartItemId);

                    return cartItemRepository.save(cartItem);
                }
        );
        item.setQuantity(item.getQuantity() + 1);

    }

    public String findCartIdByUserID(Long userId) {
        return cartRepository.findCartIdByUserID(userId).toString();
    }
}
