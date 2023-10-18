package com.bookstore.service.impl;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.OrderItemMapper;
import com.bookstore.model.OrderItem;
import com.bookstore.repository.order.OrderItemRepository;
import com.bookstore.service.OrderItemService;
import com.bookstore.util.PageableUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemResponseDto findById(Long orderId, Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find order item by id " + id));
        if (!orderItem.getOrder().getId().equals(orderId)) {
            new EntityNotFoundException("This order hasn't order item this id " + id);
        }
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public List<OrderItemResponseDto> findAll(int page, int size, String sort, Long orderId) {
        Pageable pageable = PageableUtil.getPageable(page, size, sort);
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId, pageable);
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
