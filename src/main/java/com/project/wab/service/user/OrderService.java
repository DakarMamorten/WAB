package com.project.wab.service.user;

import com.project.wab.domain.order.*;
import com.project.wab.dto.OrderDTO;
import com.project.wab.dto.OrderItemDTO;
import com.project.wab.dto.OrderWithItemsProjection;
import com.project.wab.exception.OrderNotFoundException;
import com.project.wab.repository.OrderRepository;
import com.project.wab.service.AddressService;
import com.project.wab.service.CartService;
import com.project.wab.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;

    private static OrderDTO getOrderDTO(UUID orderId, List<OrderWithItemsProjection> rows) {
        if (rows.isEmpty()) {
            throw new EntityNotFoundException("Order not found for id: " + orderId);
        }
        var firstRow = rows.getFirst();

        return new OrderDTO(
                firstRow.orderId(),
                firstRow.state().toString(),
                firstRow.paymentState().toString(),
                firstRow.shipmentState().toString(),
                firstRow.totalPrice(),
                new ArrayList<>()
        );
    }

    @Transactional
    public Order placeOrder(String cartId, Long addressId) {
        var order = new Order();

        var cart = cartService.findById(cartId);
        var address = addressService.findById(addressId);
        if (cart.getUserId() != null) {
            var user = userService.findById(cart.getUserId());
            order.setUser(user);
            order.setAddress(address);
            order.setUserEmail(user.getEmail());
        } else {
            order.setAddress(address);
            order.setUserEmail(address.getEmail());
        }
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty and cannot be transformed into an order.");
        }

        order.setOrderDate(LocalDateTime.now());
        order.setPaymentState(PaymentState.PENDING);
        order.setShipmentState(ShipmentState.PENDING);
        order.setState(OrderState.OPEN);

        var totalPrice = BigDecimal.ZERO;
        for (var cartItem : cart.getItems()) {
            var orderItem = new OrderItem();
            var orderItemId = new OrderItemId();
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

    public List<Order> findOrdersByUserID(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public OrderDTO getOderViewById(UUID orderId) {
        var rows = orderRepository.findOrderWithItems(orderId);

        var orderDTO = getOrderDTO(orderId, rows);

        for (var row : rows) {
            var itemDTO = new OrderItemDTO(
                    row.productName(),
                    row.priceAtTimeOfOrder(),
                    row.quantity(),
                    row.priceAtTimeOfOrder().multiply(BigDecimal.valueOf(row.quantity())),
                    row.productId(),
                    row.productImagePath()
            );
            orderDTO.getItems().add(itemDTO);
        }

        return orderDTO;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updatePaymentStatus(UUID orderId, PaymentState status) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));
        order.setPaymentState(status);
        orderRepository.save(order);
    }
}
