package com.bookstore.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    private static final String SEMICOLON_DELIMITER = ";";
    private static final String COMMA_DELIMITER = ",";

    public static Pageable getPageable(int page, int size, String sort) {
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
