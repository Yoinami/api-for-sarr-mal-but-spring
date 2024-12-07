package com.yoinami.sarr_mal_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String password;
    private String email;

    private Float height;
    private Float weight;
    private Float bmi;
    private Integer age;
    private Boolean female;

    private List<String> diseases;
    private List<String> allergies;
    private String exercise;
    private List<String> preferredFood;

    private Boolean disabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime passwordUpdatedAt;
    private String profileImageUrl;

    public User(String username,
                String password,
                String email,
                float height,
                float weight,
                int age,
                boolean female,
                List<String> diseases,
                List<String> allergies,
                String exercise,
                List<String> preferredFood) {
        this.username = username;
        this.password = password;
        this.email = email;

        this.height = height;
        this.weight = weight;
        this.female = female;
        this.age = age;
        this.exercise = exercise;
        this.preferredFood = preferredFood;
        this.diseases = diseases;
        this.allergies = allergies;

        this.disabled = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.passwordUpdatedAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "{" +
                "\"id\": \"" + id + "\"," +
                "\"username\": \"" + username + "\"," +
                "\"password\": \"" + password + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"height\": " + height + "," +
                "\"weight\": " + weight + "," +
                "\"bmi\": " + bmi + "," +
                "\"age\": " + age + "," +
                "\"isFemale\": " + female + "," +
                "\"diseases\": " + diseases + "," +
                "\"allergies\": " + allergies + "," +
                "\"exercise\": \"" + exercise + "\"," +
                "\"preferredFood\": " + preferredFood + "," +
                "\"isDisabled\": " + disabled + "," +
                "\"createdAt\": \"" + createdAt + "\"," +
                "\"updatedAt\": \"" + updatedAt + "\"," +
                "\"lastLoginAt\": \"" + lastLoginAt + "\"," +
                "\"passwordUpdatedAt\": \"" + passwordUpdatedAt + "\"," +
                "\"profileImageUrl\": \"" + profileImageUrl + "\"" +
                "}";
    }

}
