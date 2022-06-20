package com.codegym.service.impl;


import com.codegym.model.Users;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
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

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
