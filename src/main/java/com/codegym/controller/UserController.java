package com.codegym.controller;

import com.codegym.dto.request.ChangeUserDetailForm;
import com.codegym.dto.response.ResponseMessage;
import com.codegym.model.Role;
import com.codegym.model.RoleName;
import com.codegym.model.Users;
import com.codegym.service.IUserService;
import com.codegym.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("users")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<Iterable<Users>> findAll() {
        Iterable<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> findById(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @GetMapping("/next3users/{row}")
    public ResponseEntity<Iterable<Users>> getNext3Users(@PathVariable int row) {
        Iterable<Users> users = userService.getNext3User(row);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/top3")
    public ResponseEntity<Iterable<Users>> getTop3() {
        Iterable<Users> users = userService.getTop3();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search-name/{name}")
    public ResponseEntity<Iterable<Users>> findByName(@PathVariable String name) {
        Iterable<Users> users = userService.findAllByNameContaining(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search-username/{username}")
    public ResponseEntity<Optional<Users>> findByUserName(@PathVariable String username) {
        Optional<Users> users = userService.findByUsername(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody ChangeUserDetailForm changeUserDetail, @PathVariable Long id) {
        Optional<Users> usersOptional = userService.findById(id);
        usersOptional.get().setName(changeUserDetail.getName());
        usersOptional.get().setUsername(changeUserDetail.getUsername());
        usersOptional.get().setEmail(changeUserDetail.getEmail());
        if(changeUserDetail.getAvatar() != "") {
            usersOptional.get().setAvatar(changeUserDetail.getAvatar());
        }
        String strRoles = changeUserDetail.getRole();
        Set<Role> roles = new HashSet<>();
        switch (strRoles){
            case "admin":
                Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(()-> new RuntimeException("Role not found"));
                roles.add(adminRole);
                break;
            case "pm":
                Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(()-> new RuntimeException("Roel not found"));
                roles.add(pmRole);
                break;
            case "user":
                Role userRole = roleService.findByName(RoleName.USER).orElseThrow(()-> new RuntimeException("Roel not found"));
                roles.add(userRole);
                break;
        }
        usersOptional.get().setRoles(roles);
        userService.save(usersOptional.get());
        System.out.println(usersOptional.get().toString());
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<>(new ResponseMessage("Delete Success!"), HttpStatus.OK);
    }
}
