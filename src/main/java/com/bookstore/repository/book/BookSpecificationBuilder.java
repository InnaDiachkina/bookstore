package com.bookstore.repository.book;

import com.bookstore.dto.book.request.BookSearchParametersDto;
import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String AUTHOR = "author";
    private static final String COVER_IMAGE = "coverImage";
    private static final String DESCRIPTION = "description";
    private static final String ISBN = "isbn";
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private final BookSpecificationProviderManager specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (searchParameters.getAuthors() != null && searchParameters.getAuthors().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(AUTHOR)
                    .getSpecification(searchParameters.getAuthors()));
        }
        if (searchParameters.getCoverImages() != null
                && searchParameters.getCoverImages().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(COVER_IMAGE)
                    .getSpecification(searchParameters.getCoverImages()));
        }
        if (searchParameters.getDescriptions() != null
                && searchParameters.getDescriptions().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(DESCRIPTION)
                    .getSpecification(searchParameters.getDescriptions()));
        }
        if (searchParameters.getIsbns() != null && searchParameters.getIsbns().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(ISBN)
                    .getSpecification(searchParameters.getIsbns()));
        }
        if (searchParameters.getPrices() != null && searchParameters.getPrices().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(PRICE)
                    .getSpecification(searchParameters.getPrices()));
        }
        if (searchParameters.getTitles() != null && searchParameters.getTitles().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(TITLE)
                    .getSpecification(searchParameters.getTitles()));
        }
        return specification;
    }
}
