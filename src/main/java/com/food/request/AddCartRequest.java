package com.food.request;

import java.util.List;

import lombok.Data;

@Data
public class AddCartRequest {
	
	private Long foodId;
	
	private int quantity;
	
	private List<String> ingredients;

}
