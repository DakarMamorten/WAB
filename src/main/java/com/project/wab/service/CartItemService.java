package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import com.project.wab.dto.CartItemDTO;
import com.project.wab.repository.CartItemRepository;
import com.project.wab.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        for (CartItem browserItem : cartItemsBrowser) {
            boolean itemExistsInDb = false;
            for (CartItem dbItem : cartItemsDb) {
                if (dbItem.getProduct().getId().equals(browserItem.getProduct().getId())) {
                    // If the item already exists, update the quantity
                    dbItem.setQuantity(dbItem.getQuantity() + browserItem.getQuantity());
                    itemExistsInDb = true;
                    break;
                }
            }
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

    public List<CartItemDTO> getCartItemsDTOByCartId(UUID cartId) {
        return cartItemRepository.findAllByCartId(cartId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    public BigDecimal calculateTotalPriceByCartId(UUID cartId) {
//        return getCartItemsDTOByCartId(cartId).stream()
//                .map(CartItemDTO::getTotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }


    private CartItemDTO convertToDTO(CartItem item) {
        BigDecimal total = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return new CartItemDTO(
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                total,
                item.getProduct().getId(),
                item.getProduct().getImagePath()
        );
    }

    public void updateCartItemQuantity(Long productId, UUID cartId, int quantity) {
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId)
                .orElseThrow(() -> new RuntimeException("Item not found in shopping cart"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }


    public void removeCartItem(Long productId, UUID cartId) {
        cartItemRepository.deleteByProductIdAndCartId(productId, cartId);
    }
}
