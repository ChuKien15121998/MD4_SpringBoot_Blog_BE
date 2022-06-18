package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService extends IGeneralService<Blog> {
    Page<Blog> findAllByNameContaining(String name, Pageable pageable);
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findAllByCategory (Category category, Pageable pageable);
    Page<Blog> findAllByOrderByDate(Pageable pageable);
    Page<Blog> findAllByOrderByName(Pageable pageable);
    Page<Blog> findAllByOrderById(Pageable pageable);
    Iterable<Blog> findAllByCategory (Category category);
    Iterable<Blog> findAllByNameContaining(String name);
    Iterable<Blog> getNext3Blog(int row);
    Iterable<Blog> getTop3();
}
