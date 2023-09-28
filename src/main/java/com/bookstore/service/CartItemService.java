package com.bookstore.service;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.model.CartItem;
import java.util.List;

public interface CartItemService {
    CartItem findById(Long id);

    void deleteById(Long id);

    List<CartItemResponseDto> findAll(int page, int size, String sort);

    CartItemResponseDto update(int quantity, Long id);

    CartItemResponseDto save(CartItem cartItem);

    void delete(CartItem cartItem);
}
