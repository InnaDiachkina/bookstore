package com.bookstore.controller;

import com.bookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.bookstore.dto.category.request.CreateCategoryRequestDto;
import com.bookstore.dto.category.response.CategoryResponseDto;
import com.bookstore.service.BookService;
import com.bookstore.service.CategoryService;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

@Tag(name = "Category management", description = "Endpoints for managing categories")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Get all categories", description =
            "Get a list of all available categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                    @Schema(implementation = CategoryResponseDto.class)))
            })
    })
    public List<CategoryResponseDto> getAll(
            @Parameter(description = "Page number", schema = @Schema(type = "integer",
                    example = "0", defaultValue = "0"))
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", schema = @Schema(type = "integer",
                    example = "2", defaultValue = "20"))
            @RequestParam(name = "size", defaultValue = "20") int size,
            @Parameter(description = "Sorting criteria", schema = @Schema(type = "string",
                    example = "name,asc;description,desc", defaultValue = "id,asc"))
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort
    ) {
        return categoryService.findAll(page, size, sort);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category", description = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = CategoryResponseDto.class))
                    })
    })
    public CategoryResponseDto createCategory(@Parameter(description =
            "Request body for creating a new category",
            required = true, content = @Content(schema = @Schema(implementation =
            CreateCategoryRequestDto.class))) @RequestBody @Valid
                              CreateCategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Get a category by id", description = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))
            })
    })
    public CategoryResponseDto getCategoryById(@Parameter(description = "Category ID",
            in = ParameterIn.PATH, required = true, schema = @Schema(type = "integer",
            format = "int64", example = "1")) @PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a category by id", description = "Update a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))
                    })
    })
    public CategoryResponseDto update(@Parameter(description = "Category ID", in = ParameterIn.PATH,
            required = true, schema = @Schema(type = "integer", format = "int64",
            example = "1")) @PathVariable Long id, @Parameter(description =
            "Request body for updating a category", required = true,
            content = @Content(schema = @Schema(implementation = CreateCategoryRequestDto.class)))
                                  @RequestBody @Valid CreateCategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by id", description = "Delete a category by id from db")
    public void delete(@Parameter(description = "Category ID", in = ParameterIn.PATH,
            required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
                       @PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Operation(summary = "Get all books by category id", description =
            "Get list of all books by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDtoWithoutCategoryIds.class))
            })
    })
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @Parameter(description = "Page number", schema = @Schema(type = "integer",
                    example = "0", defaultValue = "0"))
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", schema = @Schema(type = "integer",
                    example = "2", defaultValue = "20"))
            @RequestParam(name = "size", defaultValue = "20") int size,
            @Parameter(description = "Sorting criteria", schema = @Schema(type = "string",
                    example = "price,asc;title,desc", defaultValue = "id,asc"))
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort,
            @Parameter(description = "Category ID", in = ParameterIn.PATH, required = true, schema =
            @Schema(type = "integer", format = "int64", example = "1")) @PathVariable Long id) {
        return bookService.findAllByCategoryId(id, page, size, sort);
    }
}
