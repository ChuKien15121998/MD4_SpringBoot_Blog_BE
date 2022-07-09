package com.codegym.service.impl;

import com.codegym.model.CategoryBlog;
import com.codegym.repository.ICategotyBlogRepository;
import com.codegym.service.ICategoryBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryBlogService implements ICategoryBlogService {

    @Autowired
    private ICategotyBlogRepository categoryRepository;

    @Override
    public Iterable<CategoryBlog> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryBlog> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public CategoryBlog save(CategoryBlog categoryBlog) {
        return categoryRepository.save(categoryBlog);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
