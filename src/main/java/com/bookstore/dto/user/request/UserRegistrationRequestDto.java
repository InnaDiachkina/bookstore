package com.bookstore.dto.user.request;

import com.bookstore.lib.FieldsValueMatch;
import com.bookstore.lib.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
@Schema(name = "UserRegistrationRequestDto",
        description = "Object for registering a new user")
public class UserRegistrationRequestDto {
    @ValidEmail
    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;
    @NotBlank
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Size(min = 8, max = 25)
    @Schema(description = "Password", example = "password")
    private String password;
    @NotBlank
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Repeat Password", example = "password")
    private String repeatPassword;
    @NotBlank
    @Schema(description = "First Name", example = "John")
    private String firstName;
    @NotBlank
    @Schema(description = "Last Name", example = "Doe")
    private String lastName;
    @NotBlank
    @Schema(description = "Shipping Address", example = "123 Main St, City, Country")
    private String shippingAddress;
}
