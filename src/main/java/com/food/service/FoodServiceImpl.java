package com.food.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.repository.FoodRepository;
import com.food.request.CreateFoodRequest;

public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		Food food=new Food();
		food.setFoodcategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasional());
		food.setVegetarian(req.isVegetarian());
		
		Food savedFood=foodRepository.save(food);
		restaurant.getFoods().add(savedFood);
		
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		
		Food food=findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.save(food);
		
	}

	@Override
	public List<Food> getRestFoods(Long restaurantId, boolean isVegitarian, boolean isNonVeg, boolean isSeasonal,
			String foodCategory) {
		List<Food> foods=foodRepository.findByRestaurant(restaurantId);
		
		
		if (isVegitarian) {
		    foods = filterByVegetarian(foods, true);
		}

		if (isNonVeg) {
		    foods = filterByNonveg(foods, true);
		}

		if (isSeasonal) { 
		    foods = filterBySeasonal(foods, true);
		}

		if (foodCategory != null && !foodCategory.isEmpty()) {
		    foods = filterByCategory(foods, foodCategory);
		}

		return foods;
	} 
	
	private List<Food> filterByVegetarian(List<Food> foods,boolean isVegitarian){
		
		return foods.stream().filter(food -> food.isVegetarian()==isVegitarian).collect(Collectors.toList());
	}
	
	private List<Food> filterByNonveg(List<Food> foods,boolean isNonVeg){
		return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
	} 
	 
	private List<Food> filterBySeasonal(List<Food> foods,boolean isSeasonal){
		return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}
	
	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
	    return foods.stream()
	                .filter(food -> food.getFoodcategory() != null &&
	                                food.getFoodcategory().getName().equals(foodCategory))
	                .collect(Collectors.toList());
	}


	@Override
	public List<Food> searchFood(String keyword) {
		// TODO Auto-generated method stub
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optional=foodRepository.findById(foodId);
		
		if(optional.isEmpty()) {
			throw new Exception("Food not exist...");
		}
		
		return optional.get();
	}

	@Override
	public Food updateAvailibiltyStatus(Long foodId) throws Exception {
		Food food=findFoodById(foodId);
		food.setAvaliable(!food.isAvaliable());
		
		return foodRepository.save(food);
	}
	

}
