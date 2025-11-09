package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.request.CreateFoodRequest;
import com.food.response.MessageResponse;
import com.food.service.FoodService;
import com.food.service.RestaurantService;
import com.food.service.UserService;

@RestController
@RequestMapping("api/admin/food")
public class AdminFoodController {

	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findByJwtToken(jwt);
		Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
		Food food=foodService.createFood(req, req.getCategory(), restaurant);
		
		return new ResponseEntity<>(food,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping
	public ResponseEntity<MessageResponse> deleteFood(@RequestBody CreateFoodRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findByJwtToken(jwt);
		foodService.deleteFood(user.getId());
		
		MessageResponse res=new MessageResponse();
		res.setMessage("food deleted successfully");
		
		return new ResponseEntity<>(res,HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvaibility(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findByJwtToken(jwt);
		
		Food food=foodService.updateAvailibiltyStatus(id);
		
		return new ResponseEntity<>(food,HttpStatus.CREATED);
		
	}
	
	
	
	
	 
}













