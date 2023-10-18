package com.bookstore.controller;

import com.bookstore.dto.orderitem.OrderItemResponseDto;
import com.bookstore.service.OrderItemService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order item management", description = "Endpoints for managing order items")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get an order item by id", description = "Get an order item by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = OrderItemResponseDto.class))})})
    public OrderItemResponseDto findById(
            @Parameter(description = "Order ID", in = ParameterIn.PATH, required = true,
                    schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long orderId,

            @Parameter(description = "Order ID", in = ParameterIn.PATH, required = true,
                    schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long id) {
        return orderItemService.findById(orderId, id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get items by order id", description = "Get a list all item by order id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array =
            @ArraySchema(schema = @Schema(implementation = OrderItemResponseDto.class)))})})
    public List<OrderItemResponseDto> getAll(
            @Parameter(description = "Order ID", in = ParameterIn.PATH, required = true,
                    schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long orderId,

            @Parameter(description = "Page number",
                    schema = @Schema(type = "integer", example = "0", defaultValue = "0"))
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Number of items per page",
                    schema = @Schema(type = "integer", example = "2", defaultValue = "20"))
            @RequestParam(name = "size", defaultValue = "20") int size,

            @Parameter(description = "Sorting criteria",
                    schema = @Schema(type = "string", example = "quantity,desc",
                            defaultValue = "id,asc"))
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort) {
        return orderItemService.findAll(page, size, sort, orderId);
    }
}
