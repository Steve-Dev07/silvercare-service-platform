package com.silvercare.dao;

import com.silvercare.dto.UserRegisterDTO;
import com.silvercare.dto.UserLoginDTO;
import com.silvercare.util.Db;
import com.silvercare.util.OperationResponse;

import java.sql.*;
import java.util.*;

public class UserDAO {
	public static OperationResponse insertNewUser(UserRegisterDTO userRegisterData) {
		boolean success = false;
		String code = "";
		Map<String, Integer> userData = new HashMap<>();
		
		try {
			Connection conn = Db.getConnection();
			String sqlStatement = "INSERT INTO user(username, email, display_name, password, role_id) "
					+ "VALUES (?, ?, ?, ?, 1)";
			PreparedStatement stmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, userRegisterData.getUsername());
			stmt.setString(2, userRegisterData.getEmail());
			stmt.setString(3, userRegisterData.getDisplayName());
			stmt.setString(4, userRegisterData.getPassword());
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected == 1) {
				success = true;
				code = "SUCCESS_USER_REGISTER";
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					userData.put("newUserId", rs.getInt(1));
					userData.put("roleId", 1);
				}
			} else {
				success = false;
				code = "ERR_USER_REGISTER_UNKNOWN";
			}
			
			conn.close();
		} catch(SQLException e) {
			success = false;

	        System.out.println("SQLException at UserDAO.insertNewUser");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());

	        if (e.getErrorCode() == 1062) {
	            if (e.getMessage().contains("username")) {
	                code = "ERR_USER_REGISTER_DUPLICATE_USERNAME";
	            } else if (e.getMessage().contains("email")) {
	                code = "ERR_USER_REGISTER_DUPLICATE_EMAIL";
	            } else {
	            	code = "ERR_USER_REGISTER_UNKNOWN";
	            }
	        } else {
	        	code = "ERR_USER_REGISTER_UNKNOWN";
	        }
		} catch(Exception e) {
			success = false;
			
			System.out.println("Unknown Exception at UserDAO.insertNewUser");
			e.printStackTrace();
		}
		
		return new OperationResponse(success, code, userData);
	}
	
	public static OperationResponse selectUserByUsername(UserLoginDTO userLoginData) {
		boolean success = false;
		String code = "";
		String message = "";
		Map<String, Object> responseData = new HashMap<>();
		
		try {
			Connection conn = Db.getConnection();
			String sqlStatement = "SELECT id, display_name, password, role_id FROM user "
					+ "WHERE username = ? LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			
			stmt.setString(1, userLoginData.getUsername());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				success = true;
				code = "SUCCESS_USER_RETRIEVE";
				
				responseData.put("userId", rs.getInt("id"));
				responseData.put("roleId", rs.getInt("role_id"));
				responseData.put("hash", rs.getString("password"));
				responseData.put("displayName", rs.getString("display_name"));
			} else {
				success = false;
				code = "ERR_USER_RETRIEVE_NOT_FOUND";
			}
			
			conn.close();
		} catch (SQLException e) {
	        System.out.println("SQLException at UserDAO.selectUserByUsername");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());

			success = false;
			code = "ERR_USER_RETRIEVE_UNKNOWN";
		}
		
		return new OperationResponse(success, code, message, responseData);
	}
}
