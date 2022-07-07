package com.codegym.repository;


import com.codegym.model.Blog;
import com.codegym.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username);

    Optional<Users> findById(Long id);

    @Query(value = "select * from users where username not like :username", nativeQuery = true)
    Iterable<Users> checkExistsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(value = "select * from users where email not like :email", nativeQuery = true)
    Iterable<Users> checkExistsByEmail(String email);
    Iterable<Users> findAllByNameContaining(String name);
    @Query(value = "select * from users limit ?, 3", nativeQuery = true)
    Iterable<Users> getNext3User(int row);

    @Query(value = "select * from users order by id limit 3 ", nativeQuery = true)
    Iterable<Users> getTop3();
}
