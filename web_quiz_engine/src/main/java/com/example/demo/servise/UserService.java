package com.example.demo.servise;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String EMAIL_EXISTS = "Email %s is already been taken";
    private final String USER_NOT_FOUND = "User not found";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User newUser) {
        Optional<User> optionalUser = findUserByEmail(newUser.getEmail());
        if (optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(EMAIL_EXISTS, newUser.getEmail()));
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findAll().stream().filter(x -> x.getEmail().equals(username)).findFirst();
        if (optUser.isPresent()) {
            User user = optUser.get();
            var builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.disabled(!user.isAuthorized());
            builder.roles(user.getRole());
            return builder.build();
        }
        throw new UsernameNotFoundException(USER_NOT_FOUND);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findAll().stream().filter(x -> email.equals(x.getEmail())).findFirst();
    }
}
