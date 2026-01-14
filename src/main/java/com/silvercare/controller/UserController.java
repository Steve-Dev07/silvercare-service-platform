package com.silvercare.controller;

import com.silvercare.dao.UserDAO;
import com.silvercare.dto.UserLoginDTO;
import com.silvercare.dto.UserRegisterDTO;
import com.silvercare.dto.UserUpdateDTO;
import com.silvercare.util.OperationResponse;
import com.silvercare.util.PasswordUtil;

import java.util.*;

public class UserController {
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
	
	public static UserUpdateDTO getUserProfileDataById(Integer userId) {
		return UserDAO.selectUserProfileById(userId);
	}
	
	public static OperationResponse updateUserProfile(String username, String email, String displayName, int userId) {
		boolean success = false;
		String code = "";
		String message = "";
		
        if (displayName == null || displayName.isEmpty()) {
            displayName = username;
        }
        
        if(!username.matches("^[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*$") || username.length() < 8 || username.length() > 32) {
        	success = false;
        	message = "Invalid username format, please try again.";
        }

		var userUpdateData = new UserUpdateDTO(userId, username, displayName, email);
		
		OperationResponse updateSqlResponse = UserDAO.updateUserById(userUpdateData);
		code = updateSqlResponse.getCode();
		
		if(updateSqlResponse.isSuccess()) {
			success = true;
			message = "Refreshing user profile...";
		} else {
			success = false;
			if(updateSqlResponse.getCode().equals("ERR_USER_UPDATE_DUPLICATE_USERNAME")) {
				message = "Username already exists. Please try again.";
			} else if (updateSqlResponse.getCode().equals("ERR_USER_UPDATE_DUPLICATE_EMAIL")) {
				message = "Email already exists. Please try again.";
			} else {
				message = "Unknown error occurred. Please try again.";
			}
		}
		
		return new OperationResponse(success, code, message, null);
	}
	
	public static OperationResponse updateUserPassword(String oldPassword, String newPassword, Integer userId) {
		boolean success = false;
		String code = "";
		String message = "";
		
		OperationResponse updateSqlResponse = UserDAO.updateUserPasswordById(oldPassword, newPassword, userId);
		code = updateSqlResponse.getCode();
		
		if(updateSqlResponse.isSuccess()) {
			success = true;
			message = "Refreshing user profile...";
		} else {
			success = false;
			if(updateSqlResponse.getCode().equals("ERR_USER_PASSWORD_UPDATE_INCORRECT_PASSWORD")) {
				message = "Current password is incorrect. Please try again.";
			} else {
				message = "Unknown error occurred. Please try again.";
			}
		}
		
		return new OperationResponse(success, code, message, null);
	}
}