package com.bookstore.dto.request;

import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] authors;
    private String[] coverImages;
    private String[] descriptions;
    private String[] isbns;
    private String[] prices;
    private String[] titles;
}
