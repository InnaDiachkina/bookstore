package com.bookstore.dto.user.request;

import com.bookstore.lib.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Schema(name = "UserLoginRequestDto", description = "Object for user authorisation")
public class UserLoginRequestDto {
    @ValidEmail
    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;
    @NotBlank
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Size(min = 8, max = 25)
    @Schema(description = "Password", example = "password")
    private String password;
}
