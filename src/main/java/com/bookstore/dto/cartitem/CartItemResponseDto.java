package com.bookstore.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CartItemResponseDto", description =
        "Object for representation a cart item from shopping cart")
public class CartItemResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
