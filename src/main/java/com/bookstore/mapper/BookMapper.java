package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.request.CreateBookRequestDto;
import com.bookstore.dto.response.BookDto;
import com.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto bookRequestDto);
}
