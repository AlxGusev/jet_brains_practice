package com.example.recipes.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.recipes.utils.Utils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void givenEMail_whenRegisterNewUser_thenEmailAlreadyExists() {
        when(userRepository.findByEmail(getDefaultEmail())).thenReturn(Optional.of(getDefaultUser()));
        assertFalse(userService.registerNewUser(getDefaultUser()));
        verifyNoInteractions(passwordEncoder);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void givenEmail_whenRegisterNewUser_thenSuccess() {
        when(userRepository.findByEmail(getDefaultDifferentEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(getDefaultPassword())).thenReturn(getEncodedPassword());

        assertTrue(userService.registerNewUser(getDefaultDifferentUser()));

        verify(passwordEncoder).encode(getDefaultPassword());
        verify(userRepository, atMost(2)).save(getRegisteredUser());
    }

}