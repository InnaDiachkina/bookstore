package com.bookstore.service;

import com.bookstore.dto.category.CategoryResponseDto;
import com.bookstore.dto.category.CreateCategoryRequestDto;
import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> findAll(int page, int size, String sort);

    CategoryResponseDto findById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDtoDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDtoDto);

    void deleteById(Long id);
}
