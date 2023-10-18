package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.model.CartItem;
import com.bookstore.repository.book.BookRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = BookRepository.class)
public interface CartItemMapper {

    @Named("CartItemToDto")
    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "FindBookById")
    CartItem toModel(CreateCartItemRequestDto requestDto);
}
