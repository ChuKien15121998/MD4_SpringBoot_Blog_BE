package com.codegym.controller;

import com.codegym.dto.response.ResponseMessage;
import com.codegym.model.Category;
import com.codegym.model.Users;
import com.codegym.security.userpincal.UserDetailService;
import com.codegym.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @Autowired
    UserDetailService userDetailService;

    @GetMapping
    public ResponseEntity<?> pageCategory(@PageableDefault(sort = "nameCategory", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Users users = userDetailService.getCurrentUser();
        if (!users.getUsername().equals("Anonymous")) {
            if (categoryService.existsByNameCategory(category.getNameCategory())) {
                return new ResponseEntity<>(new ResponseMessage("no_name_category"), HttpStatus.OK);
            }
            if (category.getAvatarCategory() == null) {
                return new ResponseEntity<>(new ResponseMessage("no_avatar-category"), HttpStatus.OK);
            }
            categoryService.save(category);
            return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("create_failed"), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(category.get().getId());
        return new ResponseEntity<>(new ResponseMessage("Delete Success"), HttpStatus.OK);
    }
}
