package com.example.recipes.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.example.recipes.utils.Utils.EMAIL;
import static com.example.recipes.utils.Utils.getUser;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private IUserRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void givenEmail_whenFindByEmail_thenSuccess() {

        repository.save(getUser());

        Optional<User> userByEmail = repository.findByEmail(EMAIL);

        assertTrue(userByEmail.isPresent());
    }

    @Test
    void givenEmail_whenFindByEmail_thenNotFound() {

        Optional<User> userByEmail = repository.findByEmail(EMAIL);

        assertTrue(userByEmail.isEmpty());
    }
}