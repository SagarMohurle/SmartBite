package com.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.IngredientsCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategory, Long> {
		
	
	List<IngredientsCategory> findByRestaurant(Long id);
	
	
}
