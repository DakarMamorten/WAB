package com.project.wab.service;

import com.project.wab.domain.*;
import com.project.wab.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    public List<Order> findAll() {
        Iterable<Order> orders = orderRepository.findAll();
        return StreamSupport.stream(orders.spliterator(), false)
                .toList();
    }
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order createOrder(User user) {
        List<CartItem> cartItems = cartService.getCartItems(user);

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CREATED);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            totalAmount = totalAmount.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            order.getItems().add(orderItem);
        }

        order.setPrice(totalAmount);
        orderRepository.save(order);

        cartService.clearCart(user);

        return order;
    }
}
