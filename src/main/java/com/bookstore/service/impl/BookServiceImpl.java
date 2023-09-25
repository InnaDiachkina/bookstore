package com.bookstore.service.impl;

import com.bookstore.dto.book.request.BookSearchParametersDto;
import com.bookstore.dto.book.request.CreateBookRequestDto;
import com.bookstore.dto.book.response.BookDtoWithoutCategoryIds;
import com.bookstore.dto.book.response.BookResponseDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.book.BookSpecificationBuilder;
import com.bookstore.repository.category.CategoryRepository;
import com.bookstore.service.BookService;
import com.bookstore.util.PageableUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final PageableUtil createPageable;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponseDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        book.setCategories(getCategories(bookRequestDto.getCategoryIds()));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> findAll(int page, int size, String sort) {
        Pageable pageable = createPageable.getPageable(page, size, sort);
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto findById(Long id) {
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
    public BookResponseDto update(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id " + id));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setCoverImage(bookRequestDto.getCoverImage());
        book.setDescription(bookRequestDto.getDescription());
        book.setPrice(bookRequestDto.getPrice());
        book.setCategories(getCategories(bookRequestDto.getCategoryIds()));
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookResponseDto> search(BookSearchParametersDto searchParameters,
                                        int page, int size, String sort) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        Pageable pageable = createPageable.getPageable(page, size, sort);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
          .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId,
                                      int page, int size, String sort) {
        Pageable pageable = createPageable.getPageable(page, size, sort);
        return bookRepository.findBooksByCategoryId(categoryId, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public Set<Category> getCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow((() ->
                        new EntityNotFoundException("Can't find category by id " + id))))
                .collect(Collectors.toSet());
    }
}
