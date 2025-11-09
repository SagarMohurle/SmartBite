package com.food.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.food.controller.UserController;
import com.food.dto.RestaurantDto;
import com.food.model.Address;

import com.food.model.Restaurant;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurantRepository;
import com.food.repository.UserRepository;
import com.food.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{

   
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository usereRepository;
	
//	@Override
//	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
//		
//		Address address=addressRepository.save(req.getAddress());
//		
//		Restaurant restaurant=new Restaurant();
//		
//		restaurant.setAddress(address);
//		restaurant.setContactInformation(req.getContactInformation());
//		restaurant.setCuisineType(req.getCuisineType());
//		restaurant.setDescription(req.getDescrption());
//		restaurant.setImages(req.getImages());
//		restaurant.setName(req.getName());
//		restaurant.setOpeninghours(req.getOpeningHours());
//		restaurant.setRegestrationDate(LocalDateTime.now());
//		restaurant.setOwner(user);
//		
//		return restaurantRepository.save(restaurant);
//	}
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception {

	    // ✅ Step 1: Fetch the managed user entity from DB
	    User owner = usereRepository.findById(user.getId())
	            .orElseThrow(() -> new Exception("User not found with id: " + user.getId()));

	    // ✅ Step 2: Save the address
	    Address address = addressRepository.save(req.getAddress());

	    // ✅ Step 3: Build and save the restaurant
	    Restaurant restaurant = new Restaurant();
	    restaurant.setAddress(address);
	    restaurant.setContactInformation(req.getContactInformation());
	    restaurant.setCuisineType(req.getCuisineType());
	    restaurant.setDescription(req.getDescrption());
	    restaurant.setImages(req.getImages());
	    restaurant.setName(req.getName());
	    restaurant.setOpeninghours(req.getOpeningHours());
	    restaurant.setRegestrationDate(LocalDateTime.now());
	    restaurant.setOwner(owner); // use the managed entity here
	    restaurant.setOpen(false);

	    return restaurantRepository.save(restaurant);
	}

	

	
//	@Override
//	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) throws Exception {
//
//	    // Fetch the managed user entity from DB
//	    User existingUser = usereRepository.findById(user.getId())
//	            .orElseThrow(() -> new Exception("User not found with id " + user.getId()));
//
//	    // Save address and link to user
//	    Address address = req.getAddress();
//	    address.setUser(existingUser);
//	    address = addressRepository.save(address);
//
//	    // Create restaurant
//	    Restaurant restaurant = new Restaurant();
//	    restaurant.setOwner(existingUser);  // managed user
//	    restaurant.setAddress(address);
//	    restaurant.setContactInformation(req.getContactInformation());
//	    restaurant.setCuisineType(req.getCuisineType());
//	    restaurant.setDescription(req.getDescrption());
//	    restaurant.setImages(req.getImages());
//	    restaurant.setName(req.getName());
//	    restaurant.setOpeninghours(req.getOpeningHours());
//	    restaurant.setRegestrationDate(LocalDateTime.now());
//	    restaurant.setOpen(false);
//
//	    return restaurantRepository.save(restaurant);
//	}

	@Override 
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		
		Restaurant restaurant=findRestaurantById(restaurantId);	
		
		if(updatedRestaurant.getCuisineType()!=null) {
			restaurant.setCuisineType(updatedRestaurant.getCuisineType());
		}
		
		if(updatedRestaurant.getDescrption()!=null) {
			restaurant.setDescription(updatedRestaurant.getDescrption());
		}
		
		if(updatedRestaurant.getName()!=null) {
			restaurant.setName(updatedRestaurant.getName());
		}
			
		return restaurantRepository.save(restaurant);
	}
 
	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAll() {
				
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurants(String keyword) {
		return restaurantRepository.findyBySearchQuery(keyword);
	}
  
	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		
		Optional<Restaurant> opt=restaurantRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("restaurant not found with id"+id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		
		Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
		
		if(restaurant==null) {
			throw new Exception("restaurant with owner id not found"+userId);
		}
		
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		RestaurantDto dto=new RestaurantDto();
		
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
//		if(user.getFavorites().contains(dto)) {
//			user.getFavorites().remove(dto);
//		}
//		else {
//			user.getFavorites().add(dto);
//		}
		boolean isFavorites =false;
		
		List<RestaurantDto> favorites=user.getFavorites();
		for(RestaurantDto favorite:favorites) {
			if(favorite.getId().equals(restaurantId)) {
				isFavorites=true;
				break;
			}
			
			
		}
		
		if(isFavorites) {
			favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
		}
		else {
			favorites.add(dto); 
		} 
		
		usereRepository.save(user);
		
		return dto;
	}
  
	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
	
		Restaurant restaurant=findRestaurantById(id);
		
		restaurant.setOpen(!restaurant.isOpen());
		
		return restaurantRepository.save(restaurant);
	}

}
 