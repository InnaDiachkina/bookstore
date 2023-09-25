package com.bookstore.dto.category.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "CreateCategoryRequestDto",
        description = "Object used for creating or updating a category.")
public class CreateCategoryRequestDto {
    @NotBlank
    @Schema(description = "Name", example = "category1", required = true)
    private String name;
    @Schema(description = "Description", example = "description1")
    private String description;
}
