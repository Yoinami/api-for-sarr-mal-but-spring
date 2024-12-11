package com.yoinami.sarr_mal_api.service;

import com.yoinami.sarr_mal_api.config.SecretConfig;
import com.yoinami.sarr_mal_api.payload.UnsplashResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UnsplashService {

    private static final String UNSPLASH_ACCESS_KEY_1 = SecretConfig.getUnsplashKey();
    private static final String UNSPLASH_ACCESS_KEY_2 = UNSPLASH_ACCESS_KEY_1;
    private static final String UNSPLASH_BASE_URL = "https://api.unsplash.com/search/photos";

    public String searchForFoodImage(String foodName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = buildUrl(foodName, UNSPLASH_ACCESS_KEY_2);
        // First API Key
        String imageUrl = fetchImageUrl(restTemplate, url);
        if (imageUrl.equals("/image/error.png")) {
            // Fallback to the second API key
            url = buildUrl(foodName, UNSPLASH_ACCESS_KEY_1);
            imageUrl = fetchImageUrl(restTemplate, url);
        }

        System.out.println(imageUrl);
        return imageUrl;
    }

    private String buildUrl(String foodName, String apiKey) {
        return UriComponentsBuilder.fromHttpUrl(UNSPLASH_BASE_URL)
                .queryParam("page", 1)
                .queryParam("query", foodName + " food")
                .queryParam("client_id", apiKey)
                .queryParam("per_page", 1)
                .queryParam("orientation", "landscape")
                .queryParam("content_filter", "high")
                .toUriString();
    }

    private String fetchImageUrl(RestTemplate restTemplate, String url) {
        try {
            UnsplashResponse response = restTemplate.getForObject(url, UnsplashResponse.class);
            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                String s =  response.getResults().get(0).getComapctURL().getSmall();
                System.out.println(s);
                return s;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "/image/error.png";
    }
}
