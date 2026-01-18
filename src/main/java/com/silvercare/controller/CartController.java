package com.silvercare.controller;

import com.silvercare.dto.CartDTO;
import com.silvercare.dao.CartDAO;
import com.silvercare.util.OperationResponse;

import java.util.*;

public class CartController {
	public static OperationResponse addCartItem(String serviceName, int userId) {
		boolean success = false;
		String message = "";

		OperationResponse insertCartItemSqlResponse = CartDAO.insertCartItem(serviceName, userId);
		if(insertCartItemSqlResponse.isSuccess()) {
			success = true;
			message = "Item added to cart successfully.";
		} else {
			message = "Unknown error occurred. Please try again.";
		}
		
		return new OperationResponse(success, "", message, null);
	}
	
	public static List<CartDTO> getAllCartItemsByUserId(int userId) {
		return CartDAO.selectCartItemsByUserId(userId);
	}
	
	public static OperationResponse removeCartItemById(int cartItemId) {
		boolean success = false;
		String message = "";

		OperationResponse deleteCartItemSqlResponse = CartDAO.deleteCartItemById(cartItemId);
		if(deleteCartItemSqlResponse.isSuccess()) {
			success = true;
			message = "Item removed from cart successfully.";
		} else {
			message = "Unknown error occurred. Please try again.";
		}
		
		return new OperationResponse(success, "", message, null);
	}
}