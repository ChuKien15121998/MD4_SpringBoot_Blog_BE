package com.codegym.service.impl;

import com.codegym.model.Category;
import com.codegym.model.Users;
import com.codegym.repository.ICategoryRepository;
import com.codegym.security.userpincal.UserDetailService;
import com.codegym.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    UserDetailService userDetailService;

    public Boolean existsByNameCategory(String nameCategory) {
        return categoryRepository.existsByNameCategory(nameCategory);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        Users users = userDetailService.getCurrentUser();
        category.setUsers(users);
        return categoryRepository.save(category);
    }

    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
