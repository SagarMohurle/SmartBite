package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.model.IngredientsCategory;
import com.food.model.IngredientsItem;
import com.food.request.IngredientCategoryRequest;
import com.food.request.IngredientItemRequest;
import com.food.service.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientsService ingredientsService;
	
	@PostMapping("/category")
	public ResponseEntity<IngredientsCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception{
		
		IngredientsCategory category=ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
		
		return new ResponseEntity<>(category,HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception {
		
		IngredientsItem item=ingredientsService.createIngredientItem( req.getRestaurantId(),req.getName(),req.getCategoryId());
		
		return new ResponseEntity<>(item,HttpStatus.CREATED);
	} 
	
	@PutMapping("/{id}/stoke")
	public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception{
		
		IngredientsItem item=ingredientsService.updateStock(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItem>> getRestaurantsItem(@PathVariable Long id) throws Exception{
		
		List<IngredientsItem> item=ingredientsService.findRestauranIngredientsItems(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}
	
	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientsCategory>> getRestaurantsIngredientCategory(@PathVariable Long id) throws Exception{
		
		List<IngredientsCategory> item=ingredientsService.findIngredCategoryByRestaurantId(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}
	
	
	
	
	
	

}  
