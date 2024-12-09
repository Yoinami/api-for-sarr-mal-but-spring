package com.yoinami.sarr_mal_api.repository;

import com.yoinami.sarr_mal_api.model.MealPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealPlanRepository extends MongoRepository<MealPlan, String> {

}