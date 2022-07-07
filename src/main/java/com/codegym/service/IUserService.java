package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Users;
import java.util.Optional;

public interface IUserService extends IGeneralService<Users> {
    Optional<Users> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users save(Users users);
    Iterable<Users> findAllByNameContaining(String name);
    Iterable<Users> getNext3User(int row);
    Iterable<Users> getTop3();
    Boolean checkExistsByUsername(String username);
    Boolean checkExistsByEmail(String email);
    Iterable<Users> listExistsByUsername(String username);

}
