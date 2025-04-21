package com.bloodbank.repository;

import com.bloodbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    long countByRole(String string);

    List<User> findByRole(String string);
}
