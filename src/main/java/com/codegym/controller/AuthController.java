package com.codegym.controller;

import com.codegym.dto.request.SignInForm;
import com.codegym.dto.request.SignUpForm;
import com.codegym.dto.response.JwtResponse;
import com.codegym.dto.response.ResponseMessage;
import com.codegym.model.Role;
import com.codegym.model.RoleName;
import com.codegym.model.Users;
import com.codegym.security.jwt.JwtProvider;
import com.codegym.security.userpincal.UserPrinciple;
import com.codegym.service.impl.RoleService;
import com.codegym.service.impl.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("no_user"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("no_email"), HttpStatus.OK);
        }
        if(signUpForm.getAvatar() == null || signUpForm.getAvatar().trim().isEmpty()){
            signUpForm.setAvatar("https://firebasestorage.googleapis.com/v0/b/blog-firebase-c1eff.appspot.com/o/images%2F765-default-avatar.png?alt=media&token=913a079e-dbff-4ff1-a15b-be184446f58b");
        }
        Users users = new Users(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getAvatar());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow( ()-> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        users.setRoles(roles);
        String randomCode = RandomString.make(64);
        users.setVerificationCode(randomCode);
        users.setEnabled(false);
        userService.save(users);
        String siteURL = getSiteURL(request);
        userService.sendVerificationEmail(users, siteURL);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<?> verify(@PathVariable String code) {
        if (userService.verify(code)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
        );
        // Thêm đối tượng này vào security để xử lý tiếp
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Khởi tạo jwt từ đối tượng này
        String token = jwtProvider.createToken(authentication);
        // Tạo đối tượng userprinciple từ authentication.getPrincipal
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Optional<Users> currentUser = userService.findByUsername(signInForm.getUsername());
        if (!currentUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (currentUser.get().isEnabled()) {
            return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getUsername(),userPrinciple.getEmail(), userPrinciple.getAvatar(), userPrinciple.getAuthorities()));
        }
        return new ResponseEntity<>(new ResponseMessage("Activate Email, Please!"), HttpStatus.OK);
    }
}
