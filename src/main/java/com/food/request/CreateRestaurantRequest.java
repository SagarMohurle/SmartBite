package com.food.request;

import java.util.List;

import com.food.model.Address;
import com.food.model.ContactInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantRequest {

	private Long id;
	private String name;
	private String cuisineType;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String> images; 
	private String descrption;
}
 