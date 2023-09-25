package com.bookstore.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserResponseDto",
        description = "Represents a user object for response.")
public class UserResponseDto {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;
    @Schema(description = "First name", example = "John")
    private String firstName;
    @Schema(description = "Last name", example = "Doe")
    private String lastName;
    @Schema(description = "Shipping Address", example = "123 Main St, City, Country")
    private String shippingAddress;
}
