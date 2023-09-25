package com.bookstore.service.impl;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.mapper.ShoppingCartMapperImpl;
import com.bookstore.model.CartItem;
import com.bookstore.model.ShoppingCart;
import com.bookstore.model.User;
import com.bookstore.repository.shoppingcart.ShoppingCartRepository;
import com.bookstore.repository.user.UserRepository;
import com.bookstore.service.CartItemService;
import com.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final CartItemService cartItemService;
    private final ShoppingCartMapperImpl shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto save(ShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toModel(requestDto);
        ShoppingCart shoppingCartSaved = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCartSaved);
    }

    @Override
    public CartItemResponseDto updateQuantity(String email, int quantity, Long id) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by email"));
        return cartItemService.update(quantity, id);
    }

    @Override
    public ShoppingCartResponseDto findByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by email"));
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(user.getId()));
    }

    @Override
    public CartItemResponseDto addCartItemToShoppingCart(String email,
                                  CreateCartItemRequestDto requestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by email"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemService.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemService.deleteById(cartItemId);
    }
}
