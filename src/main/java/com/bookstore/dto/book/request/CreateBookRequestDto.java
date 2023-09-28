package com.bookstore.dto.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
@Schema(name = "CreateBookRequestDto",
        description = "Object for creating or updating a book.")
public class CreateBookRequestDto {
    @NotBlank
    @Schema(description = "Title", example = "book1")
    private String title;
    @NotBlank
    @Schema(description = "Author", example = "author1")
    private String author;
    @ISBN
    @Schema(description = "ISBN", example = "978-0735619678")
    private String isbn;
    @DecimalMin(value = "0.00")
    @Schema(description = "Price", example = "100")
    private BigDecimal price;
    @Schema(description = "Description", example = "description1")
    private String description;
    @Schema(description = "Cover image", example = "cover image1")
    private String coverImage;
    @Schema(description = "Categories associated with the book", example = "[1, 2]")
    private Set<Long> categoryIds;
}
