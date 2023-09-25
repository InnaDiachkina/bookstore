package com.bookstore.service;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto save(ShoppingCartRequestDto requestDto);

    CartItemResponseDto updateQuantity(String email, int quantity, Long id);

    ShoppingCartResponseDto findByUserEmail(String email);

    CartItemResponseDto addCartItemToShoppingCart(String email,
                                                  CreateCartItemRequestDto requestDto);

    void deleteCartItem(Long cartItemId);
}
