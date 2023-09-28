package com.bookstore.mapper;

import com.bookstore.dto.order.OrderResponseDto;
import com.bookstore.model.Order;

public interface OrderMapper {
    OrderResponseDto toDto(Order order);
}
