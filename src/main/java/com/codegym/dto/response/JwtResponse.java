package com.codegym.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    String token;
    private String type = "Bearer";
    private String name;
    private String username;
    private String email;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse() {
    }

    public JwtResponse(String token, String name,String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.name = name;
        this.avatar = avatar;
        this.roles = authorities;
    }

    public JwtResponse(String token, String name, String username, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.roles = authorities;
    }

    public JwtResponse(String token, String name, String username,String email, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.roles = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}
