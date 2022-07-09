package com.codegym.model;

import javax.persistence.*;

@Entity
@Table(name = "categorys", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "nameCategory"
        })
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameCategory;
    private String avatarCategory;
    @ManyToOne
    Users users;

    public Category() {
    }

    public Category(Long id, String nameCategory, String avatarCategory, Users users) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.avatarCategory = avatarCategory;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getAvatarCategory() {
        return avatarCategory;
    }

    public void setAvatarCategory(String avatarCategory) {
        this.avatarCategory = avatarCategory;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
