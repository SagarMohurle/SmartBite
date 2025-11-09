package com.food.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

	
	private String title;
	
	@Column(length = 1000)
	private List<String> images;
	
	private String description;
	
	private Long id;
}
 
