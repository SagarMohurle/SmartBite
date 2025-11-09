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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.model.Order;
import com.food.model.User;
import com.food.request.OrderRequest;
import com.food.service.OrderService;
import com.food.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userSevService;
	
	
	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> addItemToCart(@PathVariable Long id,
									@RequestParam(required = false) String order_status,
								@RequestHeader("Authorization") String jwt
								) throws Exception{
		
		User user=userSevService.findByJwtToken(jwt);
		
		List<Order> order=orderService.getRestaurantOrder(id,order_status);

		return new ResponseEntity<>(order,HttpStatus.OK);		
	}
	
	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
									@PathVariable String order_status,
								@RequestHeader("Authorization") String jwt
								) throws Exception{
		
		User user=userSevService.findByJwtToken(jwt);
		
		Order orders=orderService.updateOrder(orderId,order_status);

		return new ResponseEntity<>(orders,HttpStatus.OK);		
	}
	
	
	
}
