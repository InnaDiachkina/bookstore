package com.bookstore.controller;

import com.bookstore.dto.request.BookSearchParametersDto;
import com.bookstore.dto.request.CreateBookRequestDto;
import com.bookstore.dto.response.BookDto;
import com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PostMapping()
    @Operation(summary = "Create a new book", description = "Create a new book")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book by id", description = "Get a book by id")
    public BookDto getBookById(@RequestBody @PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by id", description = "Delete a book by id from db")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book by id", description = "Update a book by id")
    public BookDto update(@PathVariable Long id,
                                      @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Search all books by parameters",
            description = "Get a list of all available book by parameters")
    public List<BookDto> searchBook(BookSearchParametersDto searchParameters, Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }
}
