package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.config.JwtProvider;
import com.food.model.User;
import com.food.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	

	@Override
	public User findByJwtToken(String jwt) throws Exception {
		
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new Exception("User not found");
		}
		
		
		
		return user;
	}

}
