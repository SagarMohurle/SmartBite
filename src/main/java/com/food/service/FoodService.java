package com.food.service;

import java.util.List;

import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.request.CreateFoodRequest;

public interface FoodService {
	
	Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	List<Food> getRestFoods(Long restaurantId,boolean isVegetable,boolean isNonVeg,boolean isSeasonal,String foodCategory);
	
	List<Food> searchFood(String keyword);
	
	Food findFoodById(Long foodId) throws Exception;
	
	Food updateAvailibiltyStatus(Long foodId) throws Exception;
	
	
}
