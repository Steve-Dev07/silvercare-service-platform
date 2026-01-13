package com.silvercare.service;

import com.silvercare.dao.UserDAO;
import com.silvercare.dto.UserLoginDTO;
import com.silvercare.dto.UserRegisterDTO;
import com.silvercare.util.OperationResponse;
import com.silvercare.util.PasswordUtil;

import java.util.*;

public class UserManager {
	public static OperationResponse registerNewUser(String username, String email, String displayName, String password) {
		boolean success = false;
		String message = "";
		String code = "";
		

        if (displayName == null || displayName.isEmpty()) {
            displayName = username;
        }
        if (password == null || password.isEmpty()) {
        	success = false;
        	message = "Empty or invalid password. Please try again.";
        }
        
        if(!username.matches("^[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*$") || username.length() < 8 || username.length() > 32) {
        	success = false;
        	message = "Invalid username format, please try again.";
        }

        String hash = PasswordUtil.hashPassword(password);
		var userRegisterData = new UserRegisterDTO(username, displayName, email, hash);
		
		OperationResponse registerSqlResponse = UserDAO.insertNewUser(userRegisterData);
		code = registerSqlResponse.getCode();
		
		if(registerSqlResponse.isSuccess()) {
			success = true;
			message = "Redirecting to homepage...";
		} else {
			success = false;
			if(registerSqlResponse.getCode().equals("ERR_USER_REGISTER_DUPLICATE_USERNAME")) {
				message = "Username already exists. Please try again.";
			} else if (registerSqlResponse.getCode().equals("ERR_USER_REGISTER_DUPLICATE_EMAIL")) {
				message = "Email already exists. Please try again.";
			} else {
				message = "Unknown error occurred. Please try again.";
			}
		}
		
		return new OperationResponse(success, code, message, registerSqlResponse.getData());
	}
	
	public static OperationResponse verifyUserLogin(String username, String password) {
		boolean success = false;
		String code = "";
		String message = "";
		
        if (password == null || password.isEmpty()) {
        	success = false;
        	message = "Empty or invalid password. Please try again.";
        }
        
        if(!username.matches("^[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*$") || username.length() < 8 || username.length() > 32) {
        	success = false;
        	message = "Invalid username format, please try again.";
        }

		var userLoginData = new UserLoginDTO(username, password);
		
		OperationResponse selectUserSqlResponse = UserDAO.selectUserByUsername(userLoginData);
		code = selectUserSqlResponse.getCode();
		
		if(selectUserSqlResponse.isSuccess()) {
			@SuppressWarnings("unchecked")
			Map<String, Object> userData = (Map<String, Object>) selectUserSqlResponse.getData();
			
			String hash = (String) userData.get("hash");
			boolean isCorrectPassword = PasswordUtil.verifyPassword(password, hash);
			if(isCorrectPassword) {
				success = true;
				message = "Redirecting to homepage...";
			} else {
				success = false;
				message = "Incorrect password. Please try again.";
			}
		} else {
			success = false;
			if(selectUserSqlResponse.getCode().equals("ERR_USER_RETRIEVE_NOT_FOUND")) {
				message = "Username not found. Please try again.";
			} else {
				message = "Unknown error occurred. Please try again.";
			}
		}
		
		return new OperationResponse(success, code, message, selectUserSqlResponse.getData());
	}
}