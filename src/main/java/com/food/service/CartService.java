package com.food.service;

import org.springframework.stereotype.Service;

import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.request.AddCartRequest;

@Service
public interface CartService {
	
	CartItem addItemToCart(AddCartRequest req,String jwt) throws Exception;
	
	CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
	
	Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
	
	Long calculateCartTotals(Cart cart) throws Exception;;
	
	Cart findCartById(Long userId) throws Exception;
	
	Cart findCartByUserId(Long userId) throws Exception;
	
	Cart clearCart(String jwt) throws Exception;

}
