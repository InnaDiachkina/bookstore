package com.bookstore.dto.shoppingcart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;

@Data
@Schema(name = "ShoppingCartRequestDto",
        description = "Object for creating or updating a shopping cart.")
public class ShoppingCartRequestDto {
    @NotBlank
    @Schema(description = "User ID", example = "1")
    private Long userId;
    @Schema(description = "Set of cart item IDs", example = "[1, 2]")
    private Set<Long> cartItemIds;
}

