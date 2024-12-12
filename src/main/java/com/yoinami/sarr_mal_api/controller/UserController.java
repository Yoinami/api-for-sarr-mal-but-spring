package com.yoinami.sarr_mal_api.controller;

import com.yoinami.sarr_mal_api.model.Food;
import com.yoinami.sarr_mal_api.model.MealPlan;
import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.MealPlanRepository;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import com.yoinami.sarr_mal_api.security.JwtHelperUtils;

import com.yoinami.sarr_mal_api.service.MealPlanService;
import com.yoinami.sarr_mal_api.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private MealPlanRepository mealPlanRepository;


    //Return The Register Page for User to register
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
        Food templateFood = new Food();
        templateFood.setName("None");
        com.yoinami.sarr_mal_api.model.User account = userRepository.findItemByName(username);
        List<MealPlan> lsMealPlan = mealPlanRepository.findAllMealPlanByUserId(account.getId());
        List<Food> lsFood = MealPlanService.getEatenFoodsFromMealPlansList(lsMealPlan);
        if(lsFood.isEmpty()) lsFood.add(templateFood);

        model.addAttribute("user", account);
        model.addAttribute("numberOfMealPlan", lsMealPlan.size());
        model.addAttribute("numberOfEatenFood", lsFood.size());
        model.addAttribute("lastFood", lsFood.getLast());

        return "home";
    }


    //Delete User
    @GetMapping("/delete_me")
    public String delete(@CookieValue(value = "JWT", defaultValue = "null") String token, HttpServletResponse response) {
        try {
            userRepository.delete(userRepository.findItemByName(jwtHelper.getUsernameFromToken(token))); //delete the user
            Cookie voidCookie = controllerHelper.setUpVoidCookie();
            response.addCookie(voidCookie); //Replace JWT token with a void cookie
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/user/login?message='Successfully Deleted'";
    }

    //Update User Info
    //IDEA: we can just put id in the JWT claims in order to reduce read query to the database
    @PostMapping("/update")
    public String update(@CookieValue(value = "JWT", defaultValue = "null") String token,
                         @ModelAttribute("user") User user) {
        String username = jwtHelper.getUsernameFromToken(token);
        User oldinfo = userRepository.findItemByName(username);
        if (username.equals(user.getUsername())) {
            userService.updateUserInfo(oldinfo, user);
            return "redirect:/user/me?message='Successfully Updated'";
        }
        return "redirect:/error";
    }

    //Get Own Info
    @GetMapping("/me")
    public String getMe(@CookieValue(value = "JWT", defaultValue = "null") String token,
            Model model, @RequestParam(defaultValue = "") String message) {
        if(message != null) model.addAttribute("message", message);
        model.addAttribute("user", userRepository.findItemByName(jwtHelper.getUsernameFromToken(token)));
        return "userinfo";
    }

    //Logout of the Account
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie voidCookie = controllerHelper.setUpVoidCookie();
        response.addCookie(voidCookie);

        return "redirect:/user/login?message='Successfully Logged Out'";
    }
}
