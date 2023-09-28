package com.bookstore.dto.order;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
@Schema(name = "OrderResponseDto",
        description = "Represent an order object for response")
public class OrderResponseDto {
    @Schema(description = "Order ID", example = "1")
    private Long id;
    @Schema(description = "User ID", example = "1")
    private Long userId;
    @Schema(description = "Set items of order", example = "id : 1, bookId : 1, quantity : 1")
    private Set<OrderItemResponseDto> orderItems;
    @Schema(description = "Time creating order", example = "2023-07-23T15:20:45")
    private LocalDateTime orderDate;
    @Schema(description = "Total", example = "500")
    private BigDecimal total;
    @Schema(description = "Status", example = "DELIVERED")
    private String statusName;
}
