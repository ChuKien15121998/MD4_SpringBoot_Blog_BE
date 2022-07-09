package com.codegym.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Blog(String name, String email, Date date, String image, CategoryBlog categoryBlog) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.image = image;
        this.categoryBlog = categoryBlog;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Blog(String name, String email, Date date, CategoryBlog categoryBlog) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.categoryBlog = categoryBlog;
    }



    @ManyToOne
    @JoinColumn (name = "category_id")
    private CategoryBlog categoryBlog;

    public Blog() {
    }

    public Blog(String name, String email) {
        this.name = name;
        this.email = email;
    }



    public Blog(String name, String email, CategoryBlog categoryBlog) {
        this.name = name;
        this.email = email;
        this.categoryBlog = categoryBlog;
    }

    public CategoryBlog getCategory() {
        return categoryBlog;
    }

    public void setCategory(CategoryBlog categoryBlog) {
        this.categoryBlog = categoryBlog;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Blog[id=%d, name='%s', email='%s']", id, name, email);
    }

}
