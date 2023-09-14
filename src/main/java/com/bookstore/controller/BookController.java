package com.bookstore.controller;

import com.bookstore.dto.request.BookSearchParametersDto;
import com.bookstore.dto.request.CreateBookRequestDto;
import com.bookstore.dto.response.BookDto;
import com.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))
            })
    })
    public List<BookDto> getAll(
            @Parameter(description = "Page number", schema = @Schema(type = "integer",
                    example = "0", defaultValue = "0"))
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", schema = @Schema(type = "integer",
                    example = "2", defaultValue = "20"))
            @RequestParam(name = "size", defaultValue = "20") int size,
            @Parameter(description = "Sorting criteria", schema = @Schema(type = "string",
                    example = "price,asc;title,desc", defaultValue = "id,asc"))
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort
    ) {
        return bookService.findAll(page, size, sort);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new book", description = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = BookDto.class))
            })
    })
    public BookDto createBook(@Parameter(description = "Request body for creating a new book",
            required = true, content = @Content(schema = @Schema(implementation =
            CreateBookRequestDto.class))) @RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book by id", description = "Get a book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))
            })
    })
    public BookDto getBookById(@Parameter(description = "Book ID", in = ParameterIn.PATH,
            required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
                                   @PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book by id", description = "Delete a book by id from db")
    public void delete(@Parameter(description = "Book ID", in = ParameterIn.PATH, required = true,
            schema = @Schema(type = "integer", format = "int64", example = "1"))
                           @PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book by id", description = "Update a book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))
            })
    })
    public BookDto update(@Parameter(description = "Book ID", in = ParameterIn.PATH,
            required = true, schema = @Schema(type = "integer", format = "int64",
            example = "1")) @PathVariable Long id, @Parameter(description =
            "Request body for updating a new book", required = true,
            content = @Content(schema = @Schema(implementation = CreateBookRequestDto.class)))
            @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @GetMapping("/search")
    @Operation(summary = "Search all books by parameters",
            description = "Get a list of all available book by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))
            })
    })
    public List<BookDto> searchBook(BookSearchParametersDto searchParameters,
                @Parameter(description = "Page number", schema = @Schema(type = "integer",
                        example = "0", defaultValue = "0"))
                @RequestParam(name = "page", defaultValue = "0") int page,
                @Parameter(description = "Number of items per page", schema =
                @Schema(type = "integer", example = "2", defaultValue = "20"))
                @RequestParam(name = "size", defaultValue = "20") int size,
                @Parameter(description = "Sorting criteria", schema = @Schema(type = "string",
                        example = "price,asc;title,desc", defaultValue = "id,asc"))
                @RequestParam(name = "sort", defaultValue = "title,asc") String sort
    ) {
        return bookService.search(searchParameters, page, size, sort);
    }
}
