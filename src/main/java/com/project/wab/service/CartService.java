package com.project.wab.service;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.Product;
import com.project.wab.domain.User;
import com.project.wab.dto.CartItemDTO;
import com.project.wab.exception.CartNotFoundException;
import com.project.wab.repository.CartRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartServiceHelper cartServiceHelper;
    private final EntityManager entityManager;

    public Cart findCartByUserID(Long userID) {
        return cartRepository.findCartByUserID(userID).orElse(null);
    }

    public Cart findById(String id) {
        return cartRepository.findById(UUID.fromString(id)).orElseThrow(CartNotFoundException::new);
    }

    @Transactional
    public String createAndPopulateCart(Long productId, String cartId, User currentUser) {
        Product product = productService.getProductById(productId);

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

    @Transactional
    public Map<String, Object> mergeAndDeleteCart(UUID sourceCartId, Long userId) {
        return cartRepository.mergeAndDeleteCart(entityManager, sourceCartId == null ? null : sourceCartId.toString(), userId);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Transactional
    public List<CartItemDTO> getCartItemsDto(UUID cartId) {
        return cartRepository.findById(cartId)
                .map(cart -> cart.getItems()
                        .stream()
                        .map(this::convertToDTO)
                        .toList())
                .orElseGet(Collections::emptyList);
    }

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
}
