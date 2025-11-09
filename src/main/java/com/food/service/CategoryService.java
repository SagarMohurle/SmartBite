package com.food.service;

import java.util.List;

import com.food.model.Category;

public interface CategoryService {

	
	Category createCategory(String name,Long userId) throws Exception;
	
	List<Category> findCategoryByRestaurantId(Long id) throws Exception;
	
	Category findCategoryById(Long id) throws Exception;
}
