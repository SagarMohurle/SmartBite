package com.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.model.IngredientsCategory;
import com.food.model.IngredientsItem;
import com.food.model.Restaurant;
import com.food.repository.IngredientCategoryRepository;
import com.food.repository.IngredientItemRepository;

@Service
public class IngredientsServiceImpl implements IngredientsService{
	
	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngredientsCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		
		IngredientsCategory category=new IngredientsCategory();
		
		category.setRestaurant(restaurant);
		category.setName(name);
		
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientsCategory findIngredientCategoryById(Long id) throws Exception {
		Optional<IngredientsCategory> opt=ingredientCategoryRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientsCategory> findIngredCategoryByRestaurantId(Long id) throws Exception {
		
		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurant(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientsCategory category=findIngredientCategoryById(categoryId);
		
		IngredientsItem item=new IngredientsItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem ingredient=ingredientItemRepository.save(item);
		category.getIngredientsItem().add(ingredient);
		return ingredient;
	}

	@Override
	public List<IngredientsItem> findRestauranIngredientsItems(Long restaurantId) {
		// TODO Auto-generated method stub
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		
		Optional<IngredientsItem> optionalIngredientsItem=ingredientItemRepository.findById(id);
		if(optionalIngredientsItem.isEmpty()) {
			throw new Exception("ingredient not found");
		}
		
		IngredientsItem ingredientsItem=optionalIngredientsItem.get();
		ingredientsItem.setInStock(!ingredientsItem.isInStock()); 
		
		return ingredientItemRepository.save(ingredientsItem);
	}
	
	

}
