package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import com.project.wab.domain.Product;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.CartRepository;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public Integer totalProductByCartId(UUID cartId) {
        return cartItemRepository.totalProductByCartId(cartId);
    }

    public Integer totalProductByUserId(Long userId) {
        return cartItemRepository.totalProductByUserId(userId);
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem addProductToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
//        cartItem.setProductName(product.getName());
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    @Modifying
    public String mergeCart(final String cartToken, Long userId) {
        UUID cartUUID = UUID.fromString(cartToken); // Fetch cart items from the browser cart using cartToken
        List<CartItem> cartItemsBrowser = cartItemRepository.findAllByCartId(cartUUID);
        // Fetch the user's cart
        Cart userCart = cartService.findCartByUserID(userId);
        if (userCart == null) {
            // If the user's cart does not exist, create a new one
            userCart = new Cart();
            userCart.setUserId(userId);
            cartRepository.save(userCart);
        }
        // Fetch cart items from the user's cart
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
        } // Save all merged cart items
        cartItemRepository.saveAll(cartItemsDb);
        // Remove the browser cart items
        cartItemRepository.deleteAllItemsById(cartUUID);
        cartRepository.deleteCartById(UUID.fromString(cartToken));
        // Return the user's cart ID
        return userCart.getId().toString();
    }
//        List<CartItem> cartItemsBrowser = cartItemRepository.findAllByCartId(UUID.fromString(cartToken));
//        List<CartItem> cartItemsDb = cartItemRepository.findAllByUserId(userId);
//
//        for (CartItem browserItem : cartItemsBrowser) {
//            boolean itemExistsInDb = false;
//            for (CartItem dbItem : cartItemsDb) {
//                if (dbItem.getProduct().getId().equals(browserItem.getProduct().getId())) {
//                    // If the item already exists, update the quantity
//                    dbItem.setQuantity(dbItem.getQuantity() + browserItem.getQuantity());
//                    itemExistsInDb = true;
//                    break;
//                }
//            }
//            if (!itemExistsInDb) {
//                // Set the cart of the new item to the user's cart
//                browserItem.setCart(userCart);
//                CartItemId cartItemId = new CartItemId(userCart.getId(), browserItem.getProduct().getId());
//                browserItem.setId(cartItemId); cartItemsDb.add(browserItem); }
//        }
//        cartItemRepository.saveAll(cartItemsDb);
//
//        cartItemRepository.deleteAllItemsById(UUID.fromString(cartToken));
//        return cartService.findCartIdByUserID(userId);
}

//    public void removeCartItem(Long cartItemId) {
//        cartItemRepository.deleteById(cartItemId);
//    }

//    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
//        cartItem.setQuantity(quantity);
//        cartItemRepository.save(cartItem);
//        return cartItem;
//    }
//    public List<CartItem> getCartItemsByCartId(Long cartId) {
//        return cartItemRepository.findByCartId(cartId);
//    }

