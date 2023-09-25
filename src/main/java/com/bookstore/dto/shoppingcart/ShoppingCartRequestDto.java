package com.bookstore.dto.shoppingcart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;

@Data
@Schema(name = "ShoppingCartRequestDto",
        description = "Object for creating a new shopping cart or updating.")
public class ShoppingCartRequestDto {
    @NotBlank
    @Schema(description = "User ID", example = "1", required = true)
    private Long userId;
    @Schema(description = "Set of cart item IDs", example = "[1, 2]")
    private Set<Long> cartItemIds;
}

