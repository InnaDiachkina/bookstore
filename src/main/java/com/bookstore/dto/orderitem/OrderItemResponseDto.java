package com.bookstore.dto.orderitem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "OrderItemResponseDto",
        description = "Represent an object from order")
public class OrderItemResponseDto {
    @Schema(description = "OrderItem ID", example = "1")
    private Long id;
    @Schema(description = "Book ID", example = "1")
    private Long bookId;
    @Schema(description = "Quantity", example = "1")
    private int quantity;
}
