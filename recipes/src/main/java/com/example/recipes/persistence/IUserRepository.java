package com.example.recipes.persistence;

import com.example.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
