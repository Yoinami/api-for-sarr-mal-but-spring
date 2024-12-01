package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.LoginRequest;
import com.yoinami.sarr_mal_api.model.LoginResponse;
import com.yoinami.sarr_mal_api.restservice.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    /*
     * This class concern with Managing User Info and Auth
     * */

    //Register User
    @PostMapping("/register")
    public String register() {
        return "Registered Successfully";
    }

    //Login User
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        this.doAuthenticate(loginRequest.getUsername(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .userName(userDetails.getUsername()).build();
        return ResponseEntity.ok(response);
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!");
        }
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
