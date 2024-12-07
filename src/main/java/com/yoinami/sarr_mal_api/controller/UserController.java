package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.LoginRequest;
import com.yoinami.sarr_mal_api.model.LoginResponse;
import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import com.yoinami.sarr_mal_api.security.JwtHelperUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    /*
     * This class concern with Managing User Info and Auth
     * */

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelperUtils jwtHelper;

    @Autowired
    private ControllerHelper controllerHelper;

    @Autowired
    private UserRepository userRepository;

    List<User> userList = new ArrayList<>();


    //Register User
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("User", new User());
        return "register";
    }

    //Test Endpoint
    @PostMapping("/showAll")
    public List<User> showAll() {
        userList = userRepository.findAll();
        return userList;
    }

    //Give out Login Page
    @GetMapping("/login")
    public String login(Model model, @RequestParam(defaultValue = "") String message) {
        model.addAttribute("User", new User());
        model.addAttribute("message", message);
        return "sign-in";
    }

    //Give out Home Page
    @GetMapping("/home")
    public String home(@CookieValue(value = "JWT", defaultValue = "null") String token, Model model) {
        String username = jwtHelper.getUsernameFromToken(token);
        com.yoinami.sarr_mal_api.model.User account = userRepository.findItemByName(username);
        model.addAttribute("user", account);
        return "home";
    }


    //Delete User
    @DeleteMapping("/delete_me")
    public String delete(@RequestParam("username") String username) {
        return "Deleted Successfully";
    }

    //Update User Info
    @PutMapping("/update")
    public String update(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "Updated Successfully";
    }

    //Get Own Info
    @GetMapping("/me")
    public String getMe(Model model) {
        return "me";
    }

    //Logout of the Account
    @PutMapping("/logout")
    public String logout() {
        return "Logout Successfully";
    }
}
