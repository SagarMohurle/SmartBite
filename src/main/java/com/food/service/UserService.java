package com.food.service;

import com.food.model.User;

public interface UserService {
	
	
	public User findByJwtToken(String jwt) throws Exception; 
	
	public User findUserByEmail(String email) throws Exception;
	
	

}
