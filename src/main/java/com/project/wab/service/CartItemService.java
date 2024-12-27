package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public Integer totalProductByCartId(UUID cartId) {
        return cartItemRepository.totalProductByCartId(cartId);
    }

    public Integer totalProductByUserId(Long userId) {
        return cartItemRepository.totalProductByUserId(userId);
    }

    @Transactional
    public String mergeCart(final String cartToken, Long userId) {
        UUID cartUUID = UUID.fromString(cartToken);
        List<CartItem> cartItemsBrowser = cartItemRepository.findAllByCartId(cartUUID);

        Cart userCart = cartService.findCartByUserID(userId);
        if (userCart != null && cartUUID.equals(userCart.getId())) {
            return cartToken;
        }
        if (userCart == null) {
            Optional<Cart> optionalCart = cartRepository.findById(UUID.fromString(cartToken));
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                cart.setUserId(userId);
                cart.setExpireDate(null);
                cartRepository.save(cart);
                return cart.getId().toString();
            } else {
                userCart = new Cart();
                userCart.setUserId(userId);
                cartRepository.save(userCart);
            }
        }
        List<CartItem> cartItemsDb = cartItemRepository.findAllByUserId(userId);
        // Merge browser cart items into the user's cart
        for (CartItem browserItem : cartItemsBrowser) {
            boolean itemExistsInDb = false;
            for (CartItem dbItem : cartItemsDb) {
                if (dbItem.getProduct().getId().equals(browserItem.getProduct().getId())) {
                    // If the item already exists, update the quantity
                    dbItem.setQuantity(dbItem.getQuantity() + browserItem.getQuantity());
                    itemExistsInDb = true;
                    break;
                }
            } // If the item does not exist in the user's cart, add it
            if (!itemExistsInDb) {
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(userCart);
                newCartItem.setProduct(browserItem.getProduct());
                newCartItem.setQuantity(browserItem.getQuantity());
                CartItemId cartItemId = new CartItemId(userCart.getId(), browserItem.getProduct().getId());
                newCartItem.setId(cartItemId);
                cartItemsDb.add(newCartItem);
            }
        }
        cartItemRepository.saveAll(cartItemsDb);
        cartItemRepository.deleteAllItemsById(cartUUID);
        cartRepository.deleteCartById(UUID.fromString(cartToken));

        return userCart.getId().toString();
    }
}
