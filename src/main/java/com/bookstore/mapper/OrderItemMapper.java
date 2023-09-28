package com.bookstore.mapper;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import com.bookstore.model.CartItem;
import com.bookstore.model.OrderItem;

public interface OrderItemMapper {
    OrderItemResponseDto toDto(OrderItem orderItem);

    OrderItem toModel(CartItem cartItem);
}
