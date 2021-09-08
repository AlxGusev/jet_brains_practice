package com.example.recipes.user.security;

import com.example.recipes.user.IUserRepository;
import com.example.recipes.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
            throw new UsernameNotFoundException(String.format("Username was not found: %s", email));
        }

        User user = userByEmail.get();

        List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(ROLE_USER);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                auths);
    }
}
