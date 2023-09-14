package com.bookstore.service;

import com.bookstore.dto.request.BookSearchParametersDto;
import com.bookstore.dto.request.CreateBookRequestDto;
import com.bookstore.dto.response.BookDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(int page, int size, String sort);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto searchParameters, int page, int size, String sort);

    Pageable getPageable(int page, int size, String sort);
}
