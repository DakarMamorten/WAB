package com.project.wab.utils;

import com.project.wab.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author "Vladyslav Paun"
 */
@Service
public final class CartHelper {
    private CartHelper() {
    }

    public static BigDecimal calculateCartTotal(Collection<CartItemDTO> collection) {
        return collection
                .stream()
                .map(CartItemDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
