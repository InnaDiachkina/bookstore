package com.bookstore.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CategoryResponseDto",
        description = "Represents a category object for response.")
public class CategoryResponseDto {
    @Schema(description = "Category ID", example = "1")
    private Long id;
    @Schema(description = "Name", example = "category1")
    private String name;
    @Schema(description = "Description", example = "description1")
    private String description;
}
