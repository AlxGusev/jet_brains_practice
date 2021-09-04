package com.example.recipes.security;

import com.example.recipes.model.User;
import com.example.recipes.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class RecipeUserDetailsService implements UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";

    private final IUserRepository userRepository;

    @Autowired
    public RecipeUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (userByEmail.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with email: %s not found", email));
        }

        User user = userByEmail.get();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(ROLE_USER));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
