package com.codegym.repository;

import com.codegym.model.CategoryBlog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategotyBlogRepository extends PagingAndSortingRepository<CategoryBlog, Long> {

}
