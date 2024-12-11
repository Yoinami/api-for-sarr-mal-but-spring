package com.yoinami.sarr_mal_api.repository;

import com.yoinami.sarr_mal_api.model.MealPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MealPlanRepository extends MongoRepository<MealPlan, String> {

    MealPlan findTopByUserIdOrderByCreatedAtDesc(String userId);

    @Query(value = "{ 'userId': ?0}", sort = "{ 'createdAt': -1 }")
    List<MealPlan> findAllMealPlanByUserId(String userId);
}