package com.yoinami.sarr_mal_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yoinami.sarr_mal_api.config.SecretConfig;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public GeminiJsonResponse generateRecommendedFoodFromGemini(
            String requestBody) throws JsonProcessingException {

        String apiKey = SecretConfig.getApiKeyForGemini();
        String url = "https://generativelanguage.googleapis.com/v1beta/tunedModels/food-suggestion-ai-v3-t2z0eh7qpaq8:generateContent?key=" + apiKey;

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

    public String formatGeminiRequestBody(String json) throws Exception {
        // Create the JSON object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // Create the final structure
        ObjectNode partNode = objectMapper.createObjectNode();
        partNode.put("text", json); // Serialize the inner object as a string

        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.set("parts", objectMapper.createArrayNode().add(partNode));

        ObjectNode contentsNode = objectMapper.createObjectNode();
        contentsNode.set("contents", objectMapper.createArrayNode().add(partsNode));

        // Return the full JSON as a string
        return objectMapper.writeValueAsString(contentsNode);
    }

    public String formatGeminiRequestJson(User user, String preferred, String foodType, float sugarLevel) throws Exception {
        String gender = user.getFemale() ? "Female" : "Male";
        String sugarStatus;

        if (sugarLevel < 70) {
            sugarStatus = " (Increase sugar intake)";
        } else if (sugarLevel <= 99) {
            sugarStatus = " (Maintain normal sugar intake)";
        } else if (sugarLevel <= 125) {
            sugarStatus = " (Moderate sugar intake)";
        } else {
            sugarStatus = " (Low sugar option)";
        }

        // Manually construct the JSON string with \n
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("    \"weight\": ").append(user.getWeight()).append(",\n");
        jsonBuilder.append("    \"height\": ").append(user.getHeight()).append(",\n");
        jsonBuilder.append("    \"age\": ").append(user.getAge()).append(",\n");

        // Add diseases
        jsonBuilder.append("    \"diseases\": [");
        if (user.getDiseases() != null && !user.getDiseases().isEmpty()) {
            jsonBuilder.append(String.join(", ", user.getDiseases().stream()
                    .map(d -> "\"" + d + "\"") // Surround each disease with quotes
                    .toArray(String[]::new)));
        }
        jsonBuilder.append("],\n");

        // Add allergies
        jsonBuilder.append("    \"allergies\": [");
        if (user.getAllergies() != null && !user.getAllergies().isEmpty()) {
            jsonBuilder.append(String.join(", ", user.getAllergies().stream()
                    .map(a -> "\"" + a + "\"") // Surround each allergy with quotes
                    .toArray(String[]::new)));
        }
        jsonBuilder.append("],\n");

        // Add remaining fields
        //Removed append(sugarStatus) to test ai
        jsonBuilder.append("    \"gender\": \"").append(gender).append("\",\n");
        jsonBuilder.append("    \"exercise\": \"").append(user.getExercise()).append("\",\n");
        jsonBuilder.append("    \"preferred\": \"").append(preferred).append("\",\n");
        jsonBuilder.append("    \"food-type\": \"").append(foodType).append("\"\n");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
