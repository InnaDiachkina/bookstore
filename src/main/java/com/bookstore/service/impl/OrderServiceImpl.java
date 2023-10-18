package com.bookstore.service.impl;

import com.bookstore.dto.order.OrderResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.OrderItemMapper;
import com.bookstore.mapper.OrderMapper;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.model.OrderStatus;
import com.bookstore.model.ShoppingCart;
import com.bookstore.model.User;
import com.bookstore.repository.order.OrderRepository;
import com.bookstore.repository.order.OrderStatusRepository;
import com.bookstore.repository.shoppingcart.ShoppingCartRepository;
import com.bookstore.service.OrderService;
import com.bookstore.service.ShoppingCartService;
import com.bookstore.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository statusRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderResponseDto> getAll(String email) {
        Long userId = userService.findByEmail(email).getId();
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto updateOrderStatus(String name, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find order by id " + id));
        OrderStatus status = statusRepository.findByName(OrderStatus.StatusName.valueOf(name));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto create(String email, String shippingAddress) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus(statusRepository.findByName(OrderStatus.StatusName.NEW));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Can't create order because shopping cart is empty.");
        }
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemMapper.toModel(cartItem);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        BigDecimal total = orderItems.stream()
                .map(orderItem -> orderItem.getPrice().multiply(
                        BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        orderRepository.save(order);
        shoppingCartService.clearShoppingCart(email);
        return orderMapper.toDto(order);
    }
}
