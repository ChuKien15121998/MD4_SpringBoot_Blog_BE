package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.CategoryBlog;
import com.codegym.service.IBlogService;
import com.codegym.service.ICategoryBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryBlogControllerRest {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryBlogService categoryService;

    @GetMapping
    public ResponseEntity<Iterable<CategoryBlog>> listCategory() {
        Iterable<CategoryBlog> categories = categoryService.findAll();
        if (categories != null) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/blogs")
    public ResponseEntity<Iterable<Blog>> findBlogsByCategory(@PathVariable Optional<String> id) {
        Optional<CategoryBlog> category_blog = categoryService.findById(Long.valueOf(id.get()));
        Iterable<Blog> blogs = blogService.findAllByCategoryBlog(category_blog.get());
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

}
