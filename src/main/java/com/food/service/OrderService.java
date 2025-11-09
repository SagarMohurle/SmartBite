package com.food.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.model.Order;
import com.food.model.User;
import com.food.request.OrderRequest;

@Service
public interface OrderService {
	
	public Order createOrder(OrderRequest order,User user) throws Exception;
	
	public Order updateOrder(Long orderId,String orderStatus) throws Exception;
	
	public void cancelOrder(Long orderId) throws Exception;
	
	public List<Order> getUsersOrder(Long userId) throws Exception;
	
	public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus) throws Exception;
	
	public Order findOrderById(Long orderId) throws Exception;
	

}
