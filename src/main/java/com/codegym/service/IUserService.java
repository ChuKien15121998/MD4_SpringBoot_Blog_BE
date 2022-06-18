package com.codegym.service;

import com.codegym.model.Users;
import java.util.Optional;

public interface IUserService extends IGeneralService<Users> {
    Optional<Users> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users save(Users users);
}
