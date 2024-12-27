package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import com.project.wab.domain.Product;
import com.project.wab.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "Vladyslav Paun"
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceHelper {
    private final CartItemRepository cartItemRepository;

    public void populateCart(Product product, Cart cart) {
        cartItemRepository.findCartItemByCartIdAndProductId(cart.getId(), product.getId())
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + 1);
                            cartItemRepository.save(item);
                        },
                        () -> {
                            CartItem cartItem = new CartItem();
                            cartItem.setCart(cart);
                            cartItem.setProduct(product);
                            cartItem.setQuantity(1);

                            CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
                            cartItem.setId(cartItemId);

                            cartItemRepository.save(cartItem);
                        }
                );
    }
}
