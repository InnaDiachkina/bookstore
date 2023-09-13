package com.bookstore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
@Schema(name = "CreateBookRequestDto",
        description = "Object for creating a new book.")
public class CreateBookRequestDto {
    @NotNull
    @Schema(description = "Title", example = "book1", required = true)
    private String title;
    @NotNull
    @Schema(description = "Author", example = "author1", required = true)
    private String author;
    @NotNull
    @ISBN
    @Schema(description = "ISBN", example = "978-0735619678", required = true)
    private String isbn;
    @NotNull
    @Min(value = 0)
    @Schema(description = "Price", example = "100", required = true)
    private BigDecimal price;

    @Schema(description = "Description", example = "description1")
    private String description;

    @Schema(description = "Cover image", example = "cover image1")
    private String coverImage;
}
