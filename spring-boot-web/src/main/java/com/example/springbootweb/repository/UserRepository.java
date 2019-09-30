package com.example.springbootweb.repository;

import com.example.springbootweb.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String username);

    User findByUserNameOrEmail(String username, String email);

    // @Query(value = "SELECT * FROM USERS WHERE USER_NAME = ?1", countQuery =
    // "SELECT count(*) FROM USERS WHERE USER_NAME = ?1", nativeQuery = true)
    // Page<User> findByUsername(String username, Pageable pageable);

    // @Query("select u from User u where u.user_name like ?1%")
    // List<User> findByAndSort(String username, Sort sort);

}