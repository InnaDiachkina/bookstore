package com.bookstore.service;

import com.bookstore.dto.order.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto create(String email, String shippingAddress);

    List<OrderResponseDto> getAll(String email);

    OrderResponseDto updateOrderStatus(String statusName, Long id);
}
