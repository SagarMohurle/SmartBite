package com.food.service;

import java.util.List;

import com.food.model.IngredientsCategory;
import com.food.model.IngredientsItem;

public interface IngredientsService {
	
	IngredientsCategory createIngredientCategory(String name,Long restaurant) throws Exception ;
	
	IngredientsCategory findIngredientCategoryById(Long id) throws Exception;
	
	List<IngredientsCategory> findIngredCategoryByRestaurantId(Long id) throws Exception;
	
	IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;
	
	List<IngredientsItem> findRestauranIngredientsItems(Long restaurantId);
	
	IngredientsItem updateStock(Long id) throws Exception;

}
