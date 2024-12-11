package com.yoinami.sarr_mal_api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashResponse {
    private List<UnsplashResult> results;

    public List<UnsplashResult> getResults() {
        return results;
    }

    public void setResults(List<UnsplashResult> results) {
        this.results = results;
    }
}

