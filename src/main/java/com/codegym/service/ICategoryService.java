package com.codegym.service;

import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService extends IGeneralService<Category> {
    Boolean existsByNameCategory(String nameCategory);
    Page<Category> findAll(Pageable pageable);
    Category save(Category category);
}
