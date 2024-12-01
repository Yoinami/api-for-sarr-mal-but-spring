package com.yoinami.sarr_mal_api.controllers;

import com.yoinami.sarr_mal_api.restservice.Greeting;
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
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "username: " + username + "\npassword: " + password + "\nLogin Successfully";
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
