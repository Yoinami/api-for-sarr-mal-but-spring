package com.yoinami.sarr_mal_api.service;

import com.yoinami.sarr_mal_api.config.SecretConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class ImageSearchService {

    private final String searchEngineId = SecretConfig.getSEARCH_ENGINE_ID();

    private final List<String> apiKeys = SecretConfig.getGoogleImageSearchKeys();

    private final RestTemplate restTemplate = new RestTemplate();

    public String searchForFoodImage(String searchQuery) {
        String url = "https://www.googleapis.com/customsearch/v1";

        for (String apiKey : apiKeys) {
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("q", searchQuery)
                    .queryParam("key", apiKey)
                    .queryParam("cx", searchEngineId)
                    .queryParam("searchType", "image")
                    .build()
                    .toUri();

            try {
                Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
                if (response != null && response.containsKey("items")) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                    if (!items.isEmpty()) {
                        return (String) items.get(0).get("link");
                    }
                }
            } catch (Exception e) {
                // Log the exception and continue to the next API key
                System.err.println("Error with API key: " + apiKey + ", " + e.getMessage());
            }
        }

        return "error.png";
    }
}