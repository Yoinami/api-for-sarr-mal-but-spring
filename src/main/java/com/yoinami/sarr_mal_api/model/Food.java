package com.yoinami.sarr_mal_api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Food {

    private String imageURL;
    private String name;
    private int calories;
    private String category;
    private List<String> ingredients;
    @JsonProperty("how_to_cook") private String howToCook;

    @JsonProperty("meal_time") private String mealTime;
    private LocalDateTime eatenAt;

    @Override
    public String toString() {
        return "Food{" +
                "imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", howToCook='" + howToCook + '\'' +
                ", mealTime='" + mealTime + '\'' +
                ", eatenAt=" + eatenAt +
                '}';
    }

}
