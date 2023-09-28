package com.bookstore.mapper.impl;

import com.bookstore.dto.order.OrderResponseDto;
import com.bookstore.dto.orderitem.OrderItemResponseDto;
import com.bookstore.mapper.OrderItemMapper;
import com.bookstore.mapper.OrderMapper;
import com.bookstore.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private final OrderItemMapper orderItemMapper;
    @Override
    public OrderResponseDto toDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setUserId(order.getUser().getId());
        Set<OrderItemResponseDto> orderItems = order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
        responseDto.setOrderItems(orderItems);
        responseDto.setOrderDate(order.getOrderDate());
        responseDto.setTotal(order.getTotal());
        responseDto.setStatusName(order.getStatus().getName().name());
        return responseDto;
    }
}
