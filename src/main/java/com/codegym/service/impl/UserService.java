package com.codegym.service.impl;


import com.codegym.model.Users;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IUserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;


//    public void register(Users users, String siteURL) {
//        String encodedPassword = passwordEncoder.encode(users.getPassword());
//        users.setPassword(encodedPassword);
//
//        String randomCode = RandomString.make(64);
//        users.setVerificationCode(randomCode);
//        users.setEnabled(false);
//
//        users.save(users);
//
//        sendVerificationEmail(users, siteURL);
//    }

    public void sendVerificationEmail(Users users, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String verifyURL =siteURL + "/verify/" +users.getVerificationCode() ;
        System.out.println(verifyURL);
        String toAddress = users.getEmail();
        String fromAddress = "chuvankien151298@gmail.com";
        String senderName = "What will you have for lunch?";
        String subject = "Please verify your registration";
        String mailContent = "<p>Dear " + users.getUsername()+",</p>";
        mailContent += "<p>Please click the link below to verify your registration:</p>";
        mailContent += "<h3><a href=\""+verifyURL+"\">VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>Group What will you have for lunch?</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress,senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        mailSender.send(message);
        System.out.println("Email has been sent");
    }

    public Boolean verify(String verificationCode) {
        Users users = findByVerificationCode(verificationCode);
        System.out.println(users);
        if (users == null || users.isEnabled()) {
            return false;
        } else {
            users.setVerificationCode(null);
            users.setEnabled(true);
            userRepository.save(users);
            return true;
        }
    }

    public Users findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Iterable<Users> findAllByNameContaining(String name) {
        return userRepository.findAllByNameContaining(name);
    }

    @Override
    public Iterable<Users> getNext3User(int row) {
        return userRepository.getNext3User(row);
    }

    @Override
    public Iterable<Users> getTop3() {
        return userRepository.getTop3();
    }

    public Boolean checkExistsByUsername(String username) {
        Iterable<Users> users = userRepository.checkExistsByUsername(username);
        for (Users user: users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<Users> listExistsByUsername(String username) {
        return userRepository.checkExistsByUsername(username);
    }

    public Page<Users> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Boolean checkExistsByEmail(String email) {
        Iterable<Users> users = userRepository.checkExistsByEmail(email);
        for (Users user: users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
