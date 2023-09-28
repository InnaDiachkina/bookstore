package com.bookstore.dto.cartitem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "CreateCartItemRequestDto",
        description = "Object for add book to a shopping cart")
public class CreateCartItemRequestDto {
    @NotBlank
    @Schema(description = "Book ID", example = "1")
    private Long bookId;
    @NotBlank
    @Schema(description = "Quantity", example = "1")
    private int quantity;
}
