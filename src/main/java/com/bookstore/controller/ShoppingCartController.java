package com.bookstore.controller;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import com.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import com.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get shopping cart", description = "Get user's shopping cart")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = ShoppingCartResponseDto.class))})})
    public ShoppingCartResponseDto getShoppingCart(
            @Parameter(description = "User's authentication information", hidden = true)
            Authentication authentication) {
        String email = authentication.getName();
        return shoppingCartService.findByUserEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Add cart item", description = "Add cart item to shopping cart")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cart item added "
            + "successfully", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = CartItemResponseDto.class))})})
    public CartItemResponseDto addCartItemToShoppingCart(
            @Parameter(description = "User's authentication information", hidden = true)
            Authentication authentication,

            @Parameter(description = "Object for adding cart item", required = true)
            @RequestBody CreateCartItemRequestDto requestDto) {
        String email = authentication.getName();
        return shoppingCartService.addCartItemToShoppingCart(email, requestDto);
    }

    @PutMapping("/cart_items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Update cart item", description = "Update quantity of cart item")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cart item update "
            + "successfully", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = CartItemResponseDto.class))})})
    public CartItemResponseDto updateCartItemFromShoppingCart(
            @Parameter(description = "User's authentication information", hidden = true)
            Authentication authentication,

            @Parameter(description = "Quantity for updating cart item", required = true)
            @RequestParam int quantity,

            @Parameter(description = "ID of the cart item to update", required = true)
            @PathVariable Long cartItemId) {
        String email = authentication.getName();
        return shoppingCartService.updateQuantity(email, quantity, cartItemId);
    }

    @DeleteMapping("/cart_items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete cart item", description = "Delete cart item from shopping cart")
    public void deleteCartItemFromShoppingCart(
            @Parameter(description = "ID of the cart item to delete", required = true)
            @PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
    }
}
