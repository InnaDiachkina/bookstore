package com.bookstore.repository.book.specification;

import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DescriptionSpecificationProvider implements SpecificationProvider<Book> {
    private static final String DESCRIPTION = "description";

    @Override
    public String getKey() {
        return DESCRIPTION;
    }

    public Specification<Book> getSpecification(String[] parameters) {
        return (root, query, criteriaBuilder) -> root.get(DESCRIPTION)
                .in(Arrays.stream(parameters).toArray());
    }
}
