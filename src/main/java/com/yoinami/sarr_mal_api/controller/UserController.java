package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.LoginRequest;
import com.yoinami.sarr_mal_api.model.LoginResponse;
import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import com.yoinami.sarr_mal_api.security.JwtHelperUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
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
    @PostMapping("/register")
    public String register() {
        try {
            User newUser = controllerHelper.createUserClass();
            userRepository.save(newUser);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Registered Successfully";
    }

    //Test Endpoint
    @PostMapping("/showAll")
    public List<User> showAll() {
        userList = userRepository.findAll();
        return userList;
    }

    //Login User
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        controllerHelper.doAuthenticate(loginRequest.getUsername(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .userName(userDetails.getUsername()).build();
        return ResponseEntity.ok(response);
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
    public String getMe() {

        return "me";
    }

    //Logout of the Account
    @PutMapping("/logout")
    public String logout() {
        return "Logout Successfully";
    }
}
