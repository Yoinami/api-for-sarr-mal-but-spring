package com.yoinami.sarr_mal_api.restservice.controllers;

import com.yoinami.sarr_mal_api.restservice.Greeting;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    
    //Example Mapping
    @PostMapping("/register")
    public String register() {
        return "Registered Successfully";
    }
}
