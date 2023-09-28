package com.bookstore.mapper.impl;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import com.bookstore.mapper.OrderItemMapper;
import com.bookstore.model.CartItem;
import com.bookstore.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class OrderItemMapperImpl implements OrderItemMapper {
    @Override
    public OrderItemResponseDto toDto(OrderItem orderItem) {
        OrderItemResponseDto responseDto = new OrderItemResponseDto();
        responseDto.setId(orderItem.getId());
        responseDto.setBookId(orderItem.getBook().getId());
        responseDto.setQuantity(orderItem.getQuantity());
        return responseDto;
    }

    @Override
    public OrderItem toModel(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }
}
