package com.food.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.model.Address;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.Order;
import com.food.model.OrderItem;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.repository.AddressRepository;
import com.food.repository.OrderItemRepository;
import com.food.repository.OrderRepository;
import com.food.repository.UserRepository;
import com.food.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception {
		
		Address shipAddress=order.getDeliveryAddress();
		
		Address saveAddress=addressRepository.save(shipAddress);
		
		if(user.getAddress().contains(saveAddress)) {
			user.getAddress().add(saveAddress);
			userRepository.save(user);
		}
		
		Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
		
		Order createdOrder=new Order();
		
		createdOrder.setCustomer(user);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderstatus("PENDING");
		createdOrder.setDeliveryAddress(saveAddress);
		createdOrder.setRestaurant(restaurant);
		
		
		Cart cart=cartService.findCartByUserId(user.getId());
		
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem cartItem : cart.getItems()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem savedOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(savedOrderItem);
		} 
		Long totalPrice=cartService.calculateCartTotals(cart);
		
		createdOrder.setItems(orderItems);
		createdOrder.setToatPrice(totalPrice);
		
		Order savedOrder=orderRepository.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		
		
		return createdOrder;
	}
	
	

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {

		Order order =findOrderById(orderId);
		
		if(orderStatus.equals("OUT_FOR_DELIVERY")|| 
				orderStatus.equals("DELIVERED")||
				orderStatus.equals("COMPLETED")||
				orderStatus.equals("PENDING"))
		{
			order.setOrderstatus(orderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("please select a valid order status");
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		Order order=findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
		
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
		
		List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
		if (orderStatus != null) {
		    orders = orders.stream()
		                   .filter(order -> order.getOrderstatus().equals(orderStatus))
		                   .collect(Collectors.toList());
		}

		
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception{
		
		Optional<Order> optionalOrder=orderRepository.findById(orderId);
		
		if(optionalOrder.isEmpty()) {
			throw new Exception("order not found");
		}
		
		return optionalOrder.get();
	}

}
