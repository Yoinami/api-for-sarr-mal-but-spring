package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    public User createUserClass() {
        User newUser = new User();

        newUser.setUsername("Thihansoe");
        newUser.setPassword(passwordEncoder.encode("123456"));
        newUser.setEmail("thihansoe@gmail.com");
        newUser.setAge(21);
        newUser.setHeight(179.832f);
        newUser.setWeight(77.1107f);
        newUser.setFemale(false);
        newUser.setBmi((float) (newUser.getWeight()/ Math.pow(newUser.getHeight() * 0.01, 2)));
        newUser.setAllergies(null);
        newUser.setDiseases(null);
        newUser.setExercise("None");
        newUser.setPreferredFood(null);

        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setPasswordUpdatedAt(LocalDateTime.now());
        newUser.setDisabled(false);

        return newUser;
    }
}
