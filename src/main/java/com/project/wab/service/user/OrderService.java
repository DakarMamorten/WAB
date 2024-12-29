package com.project.wab.service.user;

import com.project.wab.domain.Address;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.order.Order;
import com.project.wab.domain.order.*;
import com.project.wab.repository.OrderRepository;
import com.project.wab.service.CartService;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;

    @Transactional
    public Order placeOrder(String cartId, Address address) {
        Order order = new Order();

        var cart = cartService.findById(cartId);

        if (cart.getUserId() != null) {
            var user = userService.findById(cart.getUserId());
            order.setUser(user);
            order.setAddress(user.getAddress());
        } else {
            order.setAddress(address);
        }
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty and cannot be transformed into an order.");
        }

        order.setOrderDate(LocalDateTime.now());
        order.setPaymentState(PaymentState.PENDING);
        order.setShipmentState(ShipmentState.PENDING);
        order.setState(OrderState.OPEN);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(order.getId());
            orderItemId.setProductId(cartItem.getProduct().getId());
            orderItem.setId(orderItemId);

            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtTimeOfOrder(cartItem.getProduct().getPrice());

            order.getItems().add(orderItem);

            totalPrice = totalPrice.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
        cartService.delete(cart);

        return order;
    }
}
