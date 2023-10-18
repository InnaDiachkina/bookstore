package com.bookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "CreateCategoryRequestDto",
        description = "Represent object for creating or updating a category.")
public class CreateCategoryRequestDto {
    @NotBlank
    @Schema(description = "Name", example = "category1")
    private String name;
    @Schema(description = "Description", example = "description1")
    private String description;
}
