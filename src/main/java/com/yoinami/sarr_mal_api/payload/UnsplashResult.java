package com.yoinami.sarr_mal_api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashResult {

    @JsonProperty("urls")
    private UnsplashUrls comapctURL;

    public UnsplashUrls getComapctURL() {
        return comapctURL;
    }

    public void setComapctURL(UnsplashUrls urls) {
        this.comapctURL = urls;
    }
}
