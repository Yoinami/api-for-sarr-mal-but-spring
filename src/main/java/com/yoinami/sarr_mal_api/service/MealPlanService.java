package com.yoinami.sarr_mal_api.service;

import com.yoinami.sarr_mal_api.model.Food;
import com.yoinami.sarr_mal_api.model.MealPlan;
import com.yoinami.sarr_mal_api.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    public void updateEatenAt(String mealPlanId, String mealType, LocalDateTime eatenAtTime) {
        Optional<MealPlan> optionalMealPlan = mealPlanRepository.findById(mealPlanId);
        if (optionalMealPlan.isPresent()) {
            MealPlan mealPlan = optionalMealPlan.get();

            switch (mealType.toLowerCase()) {
                case "breakfast_main":
                    mealPlan.getBreakfast_main().setEatenAt(eatenAtTime);
                    break;
                case "breakfast_side":
                    mealPlan.getBreakfast_side().setEatenAt(eatenAtTime);
                    break;
                case "lunch_main":
                    mealPlan.getLunch_main().setEatenAt(eatenAtTime);
                    break;
                case "lunch_side":
                    mealPlan.getLunch_side().setEatenAt(eatenAtTime);
                    break;
                case "dinner_main":
                    mealPlan.getDinner_main().setEatenAt(eatenAtTime);
                    break;
                case "dinner_side":
                    mealPlan.getDinner_side().setEatenAt(eatenAtTime);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid meal type: " + mealType);
            }

            // Save the updated MealPlan back to the database
            mealPlanRepository.save(mealPlan);
        } else {
            throw new NoSuchElementException("MealPlan with ID " + mealPlanId + " not found");
        }
    }


    public List<Food> getEatenFoods(String userId) {
        List<MealPlan> mealPlans = mealPlanRepository.findAllMealPlanByUserId(userId);

        return getEatenFoodsFromMealPlansList(mealPlans);
    }

    public static List<Food> getEatenFoodsFromMealPlansList(List<MealPlan> mealPlans) {
        List<Food> eatenFoods = new ArrayList<>();

        for (MealPlan mealPlan : mealPlans) {
            if (mealPlan.getDinner_side() != null && mealPlan.getDinner_side().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getDinner_side());
            }
            if (mealPlan.getDinner_main() != null && mealPlan.getDinner_main().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getDinner_main());
            }
            if (mealPlan.getLunch_side() != null && mealPlan.getLunch_side().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getLunch_side());
            }
            if (mealPlan.getLunch_main() != null && mealPlan.getLunch_main().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getLunch_main());
            }
            if (mealPlan.getBreakfast_side() != null && mealPlan.getBreakfast_side().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getBreakfast_side());
            }
            if (mealPlan.getBreakfast_main() != null && mealPlan.getBreakfast_main().getEatenAt() != null) {
                eatenFoods.add(mealPlan.getBreakfast_main());
            }
        }

        return eatenFoods;
    }

    public MealPlan putMealPlanImage(MealPlan mealPlan) {
        ImageSearchService googleImageSearch = new ImageSearchService();


        mealPlan.getBreakfast_main().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getBreakfast_main().getName()));
        mealPlan.getBreakfast_side().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getBreakfast_side().getName()));
        mealPlan.getLunch_main().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getLunch_main().getName()));
        mealPlan.getLunch_side().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getLunch_side().getName()));
        mealPlan.getDinner_main().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getDinner_main().getName()));
        mealPlan.getDinner_side().setImageURL(googleImageSearch.searchForFoodImage(mealPlan.getDinner_side().getName()));

        return mealPlan;
    }

}
