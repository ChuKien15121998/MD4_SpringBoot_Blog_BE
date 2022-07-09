package com.codegym.model;

import javax.persistence.*;

@Entity
@Table (name = "category")
public class CategoryBlog {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public CategoryBlog() {
    }

    public CategoryBlog(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
