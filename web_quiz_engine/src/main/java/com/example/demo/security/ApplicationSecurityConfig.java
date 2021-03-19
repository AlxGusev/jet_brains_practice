package com.example.demo.security;

import com.example.demo.repository.UserRepository;
import com.example.demo.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;


    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserRepository userRepository, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService(userRepository, passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/register", "/actuator/shutdown", "/h2/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
