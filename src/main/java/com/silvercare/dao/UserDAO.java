package com.silvercare.dao;

import com.silvercare.dto.UserRegisterDTO;
import com.silvercare.util.Db;
import com.silvercare.util.OperationResponse;

import java.sql.*;

public class UserDAO {
	public static OperationResponse insertNewUser(UserRegisterDTO userRegisterData) {
		boolean success = false;
		String code = "";
		Integer newUserId = null;
		
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
					newUserId = rs.getInt(1);
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
		
		return new OperationResponse(success, code, newUserId);
	}
}
