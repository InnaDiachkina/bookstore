package com.bookstore.controller;

import com.bookstore.dto.order.OrderResponseDto;
import com.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Place an order", description = "Place an order")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Order created",
            content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderResponseDto.class))})})
    public OrderResponseDto placeOrder(
            @Parameter(description = "User's authentication information", hidden = true)
            Authentication authentication,

            @Parameter(description = "Shipping address")
            @RequestParam String shippingAddress) {
        String email = authentication.getName();
        return orderService.create(email, shippingAddress);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get all orders", description = "Get list of all orders")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema =
            @Schema(implementation = OrderResponseDto.class)))})})
    public List<OrderResponseDto> getAll(
            @Parameter(description = "Page number",
                    schema = @Schema(type = "integer", example = "0", defaultValue = "0"))
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Number of items per page",
                    schema = @Schema(type = "integer", example = "2", defaultValue = "20"))
            @RequestParam(name = "size", defaultValue = "20") int size,

            @Parameter(description = "Sorting criteria",
                    schema = @Schema(type = "string", example = "orderDate,asc;status,desc",
                            defaultValue = "id,asc"))
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort,

            @Parameter(description = "User's authentication information", hidden = true)
            Authentication authentication) {
        String email = authentication.getName();
        return orderService.getAll(email);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update status of order", description = "Update status of order")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(schema =
            @Schema(implementation = OrderResponseDto.class)))})})
    public OrderResponseDto updateOrder(
            @Parameter(description = "Status for updating order")
            @RequestParam String status,

            @Parameter(description = "Order ID", in = ParameterIn.PATH, required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long id) {
        return orderService.updateOrderStatus(status, id);
    }
}
