package com.bookstore.service.impl;

import com.bookstore.dto.request.BookSearchParametersDto;
import com.bookstore.dto.request.CreateBookRequestDto;
import com.bookstore.dto.response.BookDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.book.BookSpecificationBuilder;
import com.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String SEMICOLON_DELIMITER = ";";
    private static final String COMMA_DELIMITER = ",";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(bookRequestDto)));
    }

    @Override
    public List<BookDto> findAll(int page, int size, String sort) {
        Pageable pageable = getPageable(page, size, sort);
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id " + id));
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id " + id));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setCoverImage(bookRequestDto.getCoverImage());
        book.setDescription(bookRequestDto.getDescription());
        book.setPrice(bookRequestDto.getPrice());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters,
                                int page, int size, String sort) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        Pageable pageable = getPageable(page, size, sort);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
          .toList();
    }

    @Override
    public Pageable getPageable(int page, int size, String sort) {
        String[] sortParts = sort.split(SEMICOLON_DELIMITER);
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortPart : sortParts) {
            String[] sortFields = sortPart.split(COMMA_DELIMITER);
            String sortField = sortFields[0];
            Sort.Direction direction = Sort.Direction.fromString(sortFields[1]);
            orders.add(new Sort.Order(direction, sortField));
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }
}
