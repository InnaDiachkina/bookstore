package com.bookstore.repository.book.specification;

import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }

    public Specification<Book> getSpecification(String[] parameters) {
        return (root, query, criteriaBuilder) -> root.get("isbn")
                .in(Arrays.stream(parameters).toArray());
    }
}
