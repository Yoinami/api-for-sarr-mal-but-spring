package com.yoinami.sarr_mal_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "mealplans")
public class MealPlan {

    @Id
    private String id;
    private String userId;

    private Food breakfast_main;
    private Food breakfast_side;
    private Food lunch_main;
    private Food lunch_side;
    private Food dinner_main;
    private Food dinner_side;

    private LocalDateTime createdAt;


}
