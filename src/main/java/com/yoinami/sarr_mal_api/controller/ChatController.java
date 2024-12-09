package com.yoinami.sarr_mal_api.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/chat")
public class ChatController {
    /*
     * This class concern with Chating with AI
     * */

    //Get The Chat History with AI
    @GetMapping("/getHistory")
    public String getHistory() {
        return "Registered Successfully";
    }

    //Send Message with AI and wait for the response
    @PostMapping("/sendMessage")
    public String sendMessage() {
        return "sendNessage";
    }
}
