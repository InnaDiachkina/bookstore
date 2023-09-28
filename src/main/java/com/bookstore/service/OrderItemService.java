package com.bookstore.service;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import java.util.List;

public interface OrderItemService {
    OrderItemResponseDto findById(Long orderId, Long id);

    List<OrderItemResponseDto> findAll(int page, int size, String sort, Long orderId);
}
