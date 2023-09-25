package com.bookstore.dto.book.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "BookSearchParametersDto",
        description = "Object for searching books by parameters.")
public class BookSearchParametersDto {
    @Schema(description = "Array of authors", example = "author1, author2")
    private String[] authors;
    @Schema(description = "Array of cover images", example = "cover image1, cover image2")
    private String[] coverImages;
    @Schema(description = "Array of descriptions", example = "description1, description2")
    private String[] descriptions;
    @Schema(description = "Array of ISBNs", example = "978-0735619678, 978-0141187761")
    private String[] isbns;
    @Schema(description = "Array of prices", example = "100,200")
    private String[] prices;
    @Schema(description = "Array of titles", example = "book1, book2")
    private String[] titles;
}
