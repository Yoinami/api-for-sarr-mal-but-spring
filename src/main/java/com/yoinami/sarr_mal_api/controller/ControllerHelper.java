package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ControllerHelper {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void doAuthenticate(String username, String password) {
        System.out.println("Authenticating: " + username);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            System.out.println("Finish Authenticating: " + username);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw e; // Re-throw if you want to propagate the error.
        }
    }

    public void saveUserToDB(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
