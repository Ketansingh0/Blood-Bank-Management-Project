package com.bloodbank.repository;

import com.bloodbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    long countByRole(String string);

    List<User> findByRole(String string);
}
