package com.codegym.controller;//package blogmanager.controller;
//
//import blogmanager.model.Blog;
//import blogmanager.service.category.ICategoryService;
//import blogmanager.model.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import blogmanager.service.blog.IBlogService;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/categories")
//public class CategoryController {
//
//    @Autowired
//    private IBlogService blogService;
//
//    @Autowired
//    private ICategoryService categoryService;
//
//    @GetMapping()
//    public ModelAndView listCategory() {
//        Iterable<Category> categories = categoryService.findAll();
//        ModelAndView modelAndView = new ModelAndView("/category/list");
//        modelAndView.addObject("categories", categories);
//        return modelAndView;
//    }
//
//    @GetMapping("/create")
//    public ModelAndView showCreateForm() {
//        ModelAndView modelAndView = new ModelAndView("/category/create");
//        modelAndView.addObject("category", new Category());
//        return modelAndView;
//    }
//
//    @PostMapping("/create")
//    public ModelAndView saveCategory(@ModelAttribute("category") Category category) {
//        categoryService.save(category);
//        ModelAndView modelAndView = new ModelAndView("redirect:/category");
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{id}")
//    public ModelAndView showEditForm(@PathVariable Long id) {
//        Optional<Category> category = categoryService.findById(id);
//        if (category.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("/category/edit");
//            modelAndView.addObject("category", category.get());
//            return modelAndView;
//
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/edit")
//    public ModelAndView updateProvince(@ModelAttribute("category") Category category) {
//        categoryService.save(category);
//        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
//        modelAndView.addObject("message", "category updated successfully");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
//        Optional<Category> category = categoryService.findById(id);
//        if (category.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("/category/delete");
//            modelAndView.addObject("category", category.get());
//            return modelAndView;
//
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/delete")
//    public String deleteCategory(@ModelAttribute("category") Category category) {
//        categoryService.remove(category.getId());
//        return "redirect:/categories";
//    }
//
//    @GetMapping("/view-category/{id}")
//    public ModelAndView sortByCategory (@PathVariable Long id, Pageable pageable) {
//        Optional<Category> category = categoryService.findById(id);
//        Iterable<Blog> blogs = blogService.findAllByCategory(category.get(), pageable);
//        ModelAndView modelAndView = new ModelAndView("/category/view");
//        modelAndView.addObject("category", category.get());
//        modelAndView.addObject("blogs", blogs);
//        return modelAndView;
//    }
//}
