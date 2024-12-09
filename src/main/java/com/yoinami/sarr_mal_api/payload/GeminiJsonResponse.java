package com.yoinami.sarr_mal_api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoinami.sarr_mal_api.model.Food;
import lombok.Getter;
import lombok.Setter;

public class GeminiJsonResponse {

    @Getter
    @Setter
    @JsonProperty("response")
    private Response response;

    @Getter
    @Setter
    public static class Response {
        @JsonProperty("breakfast")
        MealName breakfast;

        @JsonProperty("lunch")
        MealName lunch;

        @JsonProperty("dinner")
        MealName dinner;
    }

    @Getter
    @Setter
    public static class MealName {
        @JsonProperty("main_dish")
        Food mainDish;

        @JsonProperty("side_dish")
        Food sideDish;
    }
}
