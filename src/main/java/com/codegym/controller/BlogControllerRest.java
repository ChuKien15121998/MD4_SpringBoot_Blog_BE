package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.blog.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/blogs")
public class BlogControllerRest {
    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("category")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        blog.setDate(new Date());
        return new ResponseEntity<>(blogService.save(blog), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ModelAndView getAllBlog() {
        ModelAndView modelAndView = new ModelAndView("/blog/list");
        modelAndView.addObject("blogs", blogService.findAll());
        return modelAndView;
    }

    @GetMapping
    public ResponseEntity<Iterable<Blog>> findAll() {
        Iterable<Blog> blogs = blogService.findAll();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        return new ResponseEntity<>(blog.get(), HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Iterable<Blog>> findByName(@PathVariable String name) {
        Iterable<Blog> blogs = blogService.findAllByNameContaining(name);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/next3blog/{row}")
    public ResponseEntity<Iterable<Blog>> getNext3Blog(@PathVariable int row) {
        Iterable<Blog> blogs = blogService.getNext3Blog(row);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/top3")
    public ResponseEntity<Iterable<Blog>> getTop3() {
        Iterable<Blog> blogs = blogService.getTop3();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Blog> delete(@PathVariable Long id) {
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<>(blogOptional.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> edit(@RequestBody Blog blog, @PathVariable Long id) {
        Optional<Blog> blogOptional = blogService.findById(id);
        blog.setId(id);
        blog.setDate(blogOptional.get().getDate());
        blogService.save(blog);
        return new ResponseEntity<>(blogService.findById(id).get(), HttpStatus.OK);
    }

}
