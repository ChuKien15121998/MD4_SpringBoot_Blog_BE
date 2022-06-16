package com.codegym.controller;//package blogmanager.controller;
//
//import blogmanager.service.blog.IBlogService;
//import blogmanager.service.category.ICategoryService;
//import blogmanager.model.Blog;
//import blogmanager.model.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Date;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/blogs")
//public class BlogController {
//    @Autowired
//    private IBlogService blogService;
//
//    @Autowired
//    private ICategoryService categoryService;
//
//    @ModelAttribute("category")
//    public Iterable<Category> categories(){
//        return categoryService.findAll();
//    }
//
//    @GetMapping("")
//    public ModelAndView listBlog(@RequestParam("search") Optional<String> search,
//                                 @RequestParam("sort") Optional<String> sort,
//                                 @RequestParam("id") Optional<String> id,
//                                 @PageableDefault(value=5) Pageable pageable) {
//        Iterable<Blog> blogs = null;
//        if (id.isPresent()) {
//            Optional<Category> categoryOptional = categoryService.findById(Long.valueOf(id.get()));
//            blogs = blogService.findAllByCategory(categoryOptional.get(), pageable);
//            ModelAndView modelAndView = new ModelAndView("/blog/list");
//            modelAndView.addObject("category", categoryOptional.get());
//            modelAndView.addObject("blogs", blogs);
//            return modelAndView;
//        }
//        if(search.isPresent()){
//            blogs = blogService.findAllByNameContaining(search.get(), pageable);
//        } else {
//            if (!sort.isPresent()) {
//                pageable = PageRequest.of(0, 5, Sort.by("date").ascending());
//                blogs = blogService.findAll(pageable);
//            } else {
//                switch (sort.get()) {
//                    case "old":
//                        pageable = PageRequest.of(0, 5, Sort.by("date").ascending());
//                        blogs = blogService.findAll(pageable);
//                        break;
//                    case "id":
//                        blogs = blogService.findAllByOrderById(pageable);
//                        break;
//                    case "name":
//                        blogs = blogService.findAllByOrderByName(pageable);
//                        break;
//                    case "new":
//                        pageable = PageRequest.of(0, 5, Sort.by("date").descending());
//                        blogs = blogService.findAll(pageable);
//                        break;
//                }
//            }
//        }
//        ModelAndView modelAndView = new ModelAndView("/blog/list");
//        modelAndView.addObject("blogs", blogs);
//        return modelAndView;
//    }
//
////    @GetMapping("/sortdate")
////    public ModelAndView getAllByDatePage(@RequestParam("search") Optional<String> search, @PageableDefault(size = 5, sort = "date", direction = Sort.Direction.ASC) Pageable pageable) {
////        Page<Blog> blogs;
////        if(search.isPresent()){
////            blogs = blogService.findAllByNameContaining(search.get(), pageable);
////        } else {
////            blogs = blogService.findAll(pageable);
////        }
////        ModelAndView modelAndView = new ModelAndView("/blog/sortdate");
////        modelAndView.addObject("blogs", blogs);
////        return modelAndView;
////    }
//
//
//    @GetMapping("/create")
//    public ModelAndView showCreateFrom() {
//        ModelAndView modelAndView = new ModelAndView("/blog/create");
//        modelAndView.addObject("blog", new Blog());
//        return modelAndView;
//    }
//
//    @PostMapping ("/create")
//    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog) {
//        blog.setDate(new Date());
//        blogService.save(blog);
//        ModelAndView modelAndView = new ModelAndView("redirect:/blogs");
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{id}")
//    public ModelAndView showEditForm(@PathVariable Long id) {
//        Optional<Blog> blog = blogService.findById(id);
//        if (blog.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("/blog/edit");
//            modelAndView.addObject("blog", blog.get());
//            return modelAndView;
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/edit")
//    public ModelAndView updateBlog (@ModelAttribute("blog") Blog blog) {
//        blogService.save(blog);
//        ModelAndView modelAndView = new ModelAndView("redirect:/blogs");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteForm(@PathVariable Long id) {
//        Optional<Blog> blog = blogService.findById(id);
//        ModelAndView modelAndView = new ModelAndView("/blog/delete");
//        modelAndView.addObject("blog", blog);
//        return modelAndView;
//    }
//
//    @PostMapping("/delete")
//    public ModelAndView deleteBlog (@ModelAttribute("blog") Blog blog) {
//        blogService.remove(blog.getId());
//        ModelAndView modelAndView = new ModelAndView("redirect:/blogs");
//        return modelAndView;
//    }
//
//    @GetMapping("/detail/{id}")
//    public ModelAndView showDetail(@PathVariable Long id) {
//        Optional<Blog> blog = blogService.findById(id);
//        ModelAndView modelAndView = new ModelAndView("/blog/detail");
//        modelAndView.addObject("blog", blog);
//        return modelAndView;
//    }
//
//}
