package com.bookstore.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CartItemResponseDto",
        description = "Represent an object from shopping cart")
public class CartItemResponseDto {
    @Schema(description = "CartItem ID", example = "1")
    private Long id;
    @Schema(description = "Book ID", example = "1")
    private Long bookId;
    @Schema(description = "Book title", example = "title")
    private String bookTitle;
    @Schema(description = "Quantity", example = "1")
    private int quantity;
}
