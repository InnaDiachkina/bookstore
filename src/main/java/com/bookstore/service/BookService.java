package com.bookstore.service;

import com.bookstore.dto.book.request.BookSearchParametersDto;
import com.bookstore.dto.book.request.CreateBookRequestDto;
import com.bookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.bookstore.dto.book.response.BookResponseDto;
import com.bookstore.model.Category;
import java.util.List;
import java.util.Set;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto bookRequestDto);

    List<BookResponseDto> findAll(int page, int size, String sort);

    BookResponseDto findById(Long id);

    void deleteById(Long id);

    BookResponseDto update(Long id, CreateBookRequestDto bookRequestDto);

    List<BookResponseDto> search(BookSearchParametersDto searchParameters,
                                 int page, int size, String sort);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId,
                                int page, int size, String sort);

    Set<Category> getCategories(Set<Long> categoryIds);
}
