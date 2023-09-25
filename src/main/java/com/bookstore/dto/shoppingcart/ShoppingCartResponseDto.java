package com.bookstore.dto.shoppingcart;

import com.bookstore.dto.cartitem.CartItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;

@Data
@Schema(name = "ShoppingCartResponseDto",
        description = "Represents a shopping cart object for response.")
public class ShoppingCartResponseDto {
    @Schema(description = "Shopping cart ID", example = "1")
    private Long id;
    @Schema(description = "User ID", example = "1")
    private Long userId;
    @Schema(description = "Cart items", example = "[id : 1, "
            + "bookId : 1, bookTitle : Title, quantity: 1]")
    private Set<CartItemResponseDto> cartItems;
}
