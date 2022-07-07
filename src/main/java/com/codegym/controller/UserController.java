package com.codegym.controller;

import com.codegym.dto.request.ChangeAvatar;
import com.codegym.dto.request.ChangeProfileForm;
import com.codegym.dto.request.ChangeRoleForm;
import com.codegym.dto.response.ResponseMessage;
import com.codegym.model.Role;
import com.codegym.model.RoleName;
import com.codegym.model.Users;
import com.codegym.security.jwt.JwtProvider;
import com.codegym.security.jwt.JwtTokenFilter;
import com.codegym.service.IUserService;
import com.codegym.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    JwtProvider jwtProvider;
    @GetMapping
    public ResponseEntity<?> pageUser(@PageableDefault(sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Users> usersPage = userService.findAll(pageable);
        if (usersPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailUser(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Users> users = userService.findById(id);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<>(new ResponseMessage("Delete Success!"), HttpStatus.OK);
    }

    @PutMapping("/change-avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest request, @Valid @RequestBody ChangeAvatar changeAvatar) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        Users user;
        try {
            if (changeAvatar.getAvatar() == null) {
                return new ResponseEntity<>(new ResponseMessage("no"), HttpStatus.OK);
            } else {
                user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found -> username" + username));
                user.setAvatar(changeAvatar.getAvatar());
                userService.save(user);
            }
            return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new ResponseMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-profile")
    public ResponseEntity<?> changeProfile(HttpServletRequest request, @Valid @RequestBody ChangeProfileForm changeProfileForm) {
        String jwt = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromToken(jwt);
        Users user;
        try {
            if (userService.checkExistsByUsername(changeProfileForm.getUsername())) {
                return new ResponseEntity<>(new ResponseMessage("nouser"), HttpStatus.OK);
            }

            if (userService.checkExistsByEmail(changeProfileForm.getEmail())) {
                return new ResponseEntity<>(new ResponseMessage("noemail"), HttpStatus.OK);
            }
            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username" + username));
            user.setName(changeProfileForm.getName());
            if (user.getUsername().equals(changeProfileForm.getUsername())) {
                user.setUsername(changeProfileForm.getUsername());
            } else {
                if (userService.existsByUsername(changeProfileForm.getUsername())) {
                    return new ResponseEntity<>(new ResponseMessage("nouser"), HttpStatus.OK);
                } else {
                    user.setUsername(changeProfileForm.getUsername());
                }
            }
            if (user.getEmail().equals(changeProfileForm.getEmail())) {
                user.setEmail(changeProfileForm.getEmail());
            } else {
                if (userService.existsByEmail(changeProfileForm.getEmail())) {
                    return new ResponseEntity<>(new ResponseMessage("noemail"), HttpStatus.OK);
                } else {
                    user.setEmail(changeProfileForm.getEmail());
                }
            }
            userService.save(user);
            return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new ResponseMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-role/{id}")
    public ResponseEntity<?> changeRoleUser(@PathVariable Long id, @RequestBody ChangeRoleForm changeRoleForm) {
        Optional<Users> users = userService.findById(id);
        if (!users.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String strRoles = changeRoleForm.getRole();
        Set<Role> roles = new HashSet<>();
        switch (strRoles) {
            case "admin":
                Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(adminRole);
                break;
            case "pm":
                Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(pmRole);
                break;
            default:
                Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(userRole);
                break;
        }
        users.get().setRoles(roles);
        userService.save(users.get());
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
}



