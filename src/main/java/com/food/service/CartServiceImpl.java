package com.food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.Food;
import com.food.model.User;
import com.food.repository.CartItemRepository;
import com.food.repository.CartRepository;
import com.food.repository.FoodRepository;
import com.food.repository.UserRepository;
import com.food.request.AddCartRequest;

@Service
public class CartServiceImpl  implements CartService{
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private FoodService foodService;
	
	@Override
	public CartItem addItemToCart(AddCartRequest req, String jwt) throws Exception {
		
		User user=userService.findByJwtToken(jwt);
		
		Food food=foodService.findFoodById(req.getFoodId());
		
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		for(CartItem cartItem:cart.getItems()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity=cartItem.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		
		CartItem newCartItem=new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem savedCartItem=cartItemRepository.save(newCartItem);
		
		cart.getItems().add(savedCartItem);
		
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		
		if(cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		
		CartItem item=cartItemOptional.get();
		item.setQuantity(quantity);
		
		item.setTotalPrice(item.getFood().getPrice()*quantity);
		
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
	
		User user=userService.findByJwtToken(jwt);
		
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		
		if(cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		
		CartItem item=cartItemOptional.get();
		
		cart.getItems().remove(item);
		
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		
		long total=0L;
		
		for(CartItem cartItem:cart.getItems()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart=cartRepository.findById(id);
		
		if(optionalCart.isEmpty()) {
			throw new Exception("cart not found with id "+id);
		}
		return optionalCart.get();
	}

	

	@Override
	public Cart clearCart(String jwt) throws Exception {
		
		User user=userService.findByJwtToken(jwt);
		
		Cart cart=findCartByUserId(user.getId());
		
		cart.getItems().clear();
		
		return cartRepository.save(cart);
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		
		Cart cart=cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

}
