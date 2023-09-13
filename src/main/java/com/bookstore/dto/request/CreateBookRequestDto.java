package com.bookstore.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @ISBN
    private String isbn;
    @DecimalMin(value = "0.00")
    private BigDecimal price;
    private String description;
    private String coverImage;
}
