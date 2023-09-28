package com.bookstore.mapper.impl;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.model.CartItem;
import com.bookstore.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapperImpl implements CartItemMapper {
    private final BookRepository bookRepository;

    @Override
    public CartItemResponseDto toDto(CartItem cartItem) {
        CartItemResponseDto responseDto = new CartItemResponseDto();
        responseDto.setId(cartItem.getId());
        responseDto.setBookId(cartItem.getBook().getId());
        responseDto.setBookTitle(cartItem.getBook().getTitle());
        responseDto.setQuantity(cartItem.getQuantity());
        return responseDto;
    }

    @Override
    public CartItem toModel(CreateCartItemRequestDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setBook(bookRepository.findBookById(requestDto.getBookId()));
        return cartItem;
    }
}
