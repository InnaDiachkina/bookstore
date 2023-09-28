package com.bookstore.mapper.impl;

import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.mapper.ShoppingCartMapper;
import com.bookstore.model.CartItem;
import com.bookstore.model.ShoppingCart;
import com.bookstore.repository.shoppingcart.CartItemRepository;
import com.bookstore.repository.user.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShoppingCartMapperImpl implements ShoppingCartMapper {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto toDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto();
        responseDto.setId(shoppingCart.getId());
        responseDto.setUserId(shoppingCart.getUser().getId());
        responseDto.setCartItems(shoppingCart.getCartItems().stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet()));
        return responseDto;
    }

    @Override
    public ShoppingCart toModel(ShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userRepository.findById(
                requestDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("Can't find user")));
        Set<CartItem> cartItems = requestDto.getCartItemIds().stream()
                        .map(id -> cartItemRepository.findById(id).orElseThrow(() ->
                                new EntityNotFoundException("Can't find item by id" + id)))
                                .collect(Collectors.toSet());
        shoppingCart.setCartItems(cartItems);
        return shoppingCart;
    }
}
