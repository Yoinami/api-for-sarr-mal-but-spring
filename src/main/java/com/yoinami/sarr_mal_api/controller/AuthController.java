package com.yoinami.sarr_mal_api.controller;


import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import com.yoinami.sarr_mal_api.security.JwtHelperUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelperUtils jwtHelper;

    @Autowired
    private ControllerHelper controllerHelper;

    @Autowired
    private UserRepository userRepository;

    //Login User
    @PostMapping("/login")
    public String login(@ModelAttribute("User") User user,
                        HttpServletResponse response) {

        controllerHelper.doAuthenticate(user.getUsername(), user.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);

        // Set the token as a cookie
        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);  // Prevent client-side scripts from accessing the token
        cookie.setPath("/");       // Available across the application
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        response.addCookie(cookie);
        return "redirect:/user/home";
    }

    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        // Check if the user exists by attempting to fetch a User
        User user = userRepository.findItemByName(username);
        boolean exists = (user != null);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-account")
    public String confirmAccount(Model model, @ModelAttribute("User") User user) {
        // Check if the user exists by attempting to fetch a User
        user.setDiseases(new ArrayList<>());
        user.setAllergies(new ArrayList<>());
        user.setPreferredFood(new ArrayList<>());
        model.addAttribute("User", user);
        System.out.println(user);
        return "register-fill";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("User") User user) {
        try{
            controllerHelper.saveUserToDB(user);
        } catch (Exception e) {
            return "register";
        }

        return "redirect:/user/login?message='Successfully Registered'";
    }
}
