package com.bookstore.repository.book;

import com.bookstore.dto.request.BookSearchParametersDto;
import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final BookSpecificationProviderManager specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (searchParameters.getAuthors() != null && searchParameters.getAuthors().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.getAuthors()));
        }
        if (searchParameters.getCoverImages() != null
                && searchParameters.getCoverImages().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("coverImage")
                    .getSpecification(searchParameters.getCoverImages()));
        }
        if (searchParameters.getDescriptions() != null
                && searchParameters.getDescriptions().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("description")
                    .getSpecification(searchParameters.getDescriptions()));
        }
        if (searchParameters.getIsbns() != null && searchParameters.getIsbns().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.getIsbns()));
        }
        if (searchParameters.getPrices() != null && searchParameters.getPrices().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(searchParameters.getPrices()));
        }
        if (searchParameters.getTitles() != null && searchParameters.getTitles().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.getTitles()));
        }
        return specification;
    }
}
