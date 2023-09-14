package com.bookstore.repository.book.specification;

import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CoverImageSpecificationProvider implements SpecificationProvider<Book> {
    private static final String COVER_IMAGE = "coverImage";

    @Override
    public String getKey() {
        return COVER_IMAGE;
    }

    public Specification<Book> getSpecification(String[] parameters) {
        return (root, query, criteriaBuilder) -> root.get(COVER_IMAGE)
                .in(Arrays.stream(parameters).toArray());
    }
}
