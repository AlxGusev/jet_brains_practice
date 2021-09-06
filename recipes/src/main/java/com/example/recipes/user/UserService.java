package com.example.recipes.user;

import com.example.recipes.user.User;
import com.example.recipes.user.IUserRepository;
import com.example.recipes.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> registerNewUser(UserDto userDto) {

        User user = convertToUserEntity(userDto);

        if (emailExist(user.getEmail())) {
            return ResponseEntity.badRequest().body(String.format("Email : %s already exists.", user.getEmail()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private boolean emailExist(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    private User convertToUserEntity(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getPassword(), false, null);
    }
}
