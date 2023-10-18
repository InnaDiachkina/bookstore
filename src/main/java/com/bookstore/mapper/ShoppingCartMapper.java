package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.shoppingcart.ShoppingCartRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.bookstore.model.ShoppingCart;
import com.bookstore.service.CartItemService;
import com.bookstore.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class,
        UserService.class, CartItemService.class})
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "shoppingCart.user.id")
    @Mapping(target = "cartItems", source = "shoppingCart.cartItems",
            qualifiedByName = "CartItemToDto")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "user", source = "userId", qualifiedByName = "FindUserById")
    @Mapping(target = "cartItems", source = "cartItemIds", qualifiedByName = "FindCartItemById")
    ShoppingCart toModel(ShoppingCartRequestDto requestDto);
}
