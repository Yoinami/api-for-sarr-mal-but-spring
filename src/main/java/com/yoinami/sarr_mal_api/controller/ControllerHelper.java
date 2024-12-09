package com.yoinami.sarr_mal_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoinami.sarr_mal_api.config.SecretConfig;
import com.yoinami.sarr_mal_api.model.Food;
import com.yoinami.sarr_mal_api.payload.GeminiJsonResponse;
import com.yoinami.sarr_mal_api.payload.GeminiResponse;
import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

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
        user.setPassword(passwordEncoder.encode(user.getPassword())); //encode password
        user.setPasswordUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public Cookie setUpVoidCookie() {
        jakarta.servlet.http.Cookie tokenCookie = new jakarta.servlet.http.Cookie("JWT", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);

        return tokenCookie;
    }

    public GeminiJsonResponse generateRecommendedFoodFromGemini() throws JsonProcessingException {

        String apiKey = SecretConfig.getApiKeyForGemini();
        String url = "https://generativelanguage.googleapis.com/v1beta/tunedModels/food-suggestion-ai-v3-t2z0eh7qpaq8:generateContent?key=" + apiKey;
        String requestBody = "{\n" +
                "    \"contents\": [\n" +
                "        {\n" +
                "            \"parts\": [\n" +
                "                {\n" +
                "                    \"text\": \"{\\n    \\\"weight\\\": 72.0,\\n    \\\"height\\\": 176.0,\\n    \\\"age\\\": 20,\\n    \\\"diseases\\\": [[\\\"adhd\\\"]],\\n    \\\"allergies\\\": [[\\\"sea food\\\"]],\\n    \\\"gender\\\": \\\"Female\\\",\\n    \\\"exercise\\\": \\\"none\\\",\\n    \\\"preferred\\\": \\\"Burmese\\\",\\n    \\\"food-type\\\": \\\"Soup (Moderate sugar intake)\\\"}\\n\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send the request using RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println(response.getBody());

        // Parse the response using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse geminiResponse = objectMapper.readValue(response.getBody(), GeminiResponse.class);

        // Extract the text from the response
        String text = geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
        text = text.substring(8, text.length() - 3); //Strip ```json and ``` from the String

        return objectMapper.readValue(text, GeminiJsonResponse.class);
    }
}
