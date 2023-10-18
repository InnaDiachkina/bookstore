package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.book.request.CreateBookRequestDto;
import com.bookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.bookstore.dto.book.response.BookResponseDto;
import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.repository.book.BookRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookRepository.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto responseDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        responseDto.setCategoryIds(categoryIds);
    }
}
