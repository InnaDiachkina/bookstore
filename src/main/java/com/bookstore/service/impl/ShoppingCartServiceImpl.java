package com.bookstore.service.impl;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.mapper.impl.ShoppingCartMapperImpl;
import com.bookstore.model.CartItem;
import com.bookstore.model.ShoppingCart;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.shoppingcart.CartItemRepository;
import com.bookstore.repository.shoppingcart.ShoppingCartRepository;
import com.bookstore.service.CartItemService;
import com.bookstore.service.ShoppingCartService;
import com.bookstore.service.UserService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final ShoppingCartMapperImpl shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto save(ShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCartSaved =
                shoppingCartRepository.save(shoppingCartMapper.toModel(requestDto));
        return shoppingCartMapper.toDto(shoppingCartSaved);
    }

    @Override
    public CartItemResponseDto updateQuantity(String email, int quantity, Long id) {
        ShoppingCart shoppingCart =
                shoppingCartRepository.findByUserId(userService.findByEmail(email).getId());
        try {
            shoppingCart.getCartItems().contains(cartItemService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(
                    "Can't find cart item in shopping cart by this id" + id);
        }
        return cartItemService.update(quantity, id);
    }

    @Override
    public ShoppingCartResponseDto findByUserEmail(String email) {
        Long userId = userService.findByEmail(email).getId();
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(userId));
    }

    @Override
    public CartItemResponseDto addCartItemToShoppingCart(String email,
                                  CreateCartItemRequestDto requestDto) {
        Long userId = userService.findByEmail(email).getId();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
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

    @Override
    public void registerNewShoppingCart(String email) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userService.findByEmail(email));
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(String email) {
        Long userId = userService.findByEmail(email).getId();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        for (CartItem cartItem : cartItems) {
            cartItem.setDeleted(true);
            cartItemRepository.save(cartItem);
        }
        shoppingCart.setCartItems(new HashSet<>());
        shoppingCartRepository.save(shoppingCart);
    }
}
