package com.bookstore.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(name = "BookDto",
        description = "Represents a book object for response.")
public class BookDto {
    @Schema(description = "Book ID", example = "1")
    private Long id;
    @Schema(description = "Title", example = "book1")
    private String title;
    @Schema(description = "Author", example = "author1")
    private String author;
    @Schema(description = "ISBN", example = "978-0735619678")
    private String isbn;
    @Schema(description = "Price", example = "100")
    private BigDecimal price;
    @Schema(description = "Description", example = "description1")
    private String description;
    @Schema(description = "Cover image", example = "cover image1")
    private String coverImage;
}
