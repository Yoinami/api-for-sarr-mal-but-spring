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

    private float height;
    private float weight;
    private float bmi;
    private int age;
    private boolean isFemale;
    private List<String> diseases;
    private List<String> allergies;
    private String exercise;
    private List<String> preferredFood;

    private boolean isDisabled;
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
                boolean isFemale,
                List<String> diseases,
                List<String> allergies,
                String exercise,
                List<String> preferredFood) {
        this.username = username;
        this.password = password;
        this.email = email;

        this.height = height;
        this.weight = weight;
        this.bmi = age;
        this.isFemale = isFemale;
        this.age = age;
        this.isDisabled = false;
        this.exercise = exercise;
        this.preferredFood = preferredFood;
        this.diseases = diseases;
        this.allergies = allergies;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.passwordUpdatedAt = LocalDateTime.now();
    }
}
