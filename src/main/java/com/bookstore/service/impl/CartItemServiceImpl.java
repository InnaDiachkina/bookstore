package com.bookstore.service.impl;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.model.CartItem;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.shoppingcart.CartItemRepository;
import com.bookstore.service.CartItemService;
import com.bookstore.util.PageableUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;
    private final PageableUtil createPageable;

    @Override
    public CartItem findById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + id));
        return cartItem;
    }

    @Override
    public void deleteById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + id));
        cartItemRepository.deleteById(id);
    }

    @Override
    public List<CartItemResponseDto> findAll(int page, int size, String sort) {
        Pageable pageable = createPageable.getPageable(page, size, sort);
        return cartItemRepository.findAll(pageable).stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponseDto update(int quantity, Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find cart item by id " + id));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemResponseDto save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }
}
