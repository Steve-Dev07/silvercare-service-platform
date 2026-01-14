package com.silvercare.dao;

import com.silvercare.dto.UserRegisterDTO;
import com.silvercare.dto.UserUpdateDTO;
import com.silvercare.dto.UserLoginDTO;
import com.silvercare.util.Db;
import com.silvercare.util.OperationResponse;
import com.silvercare.util.PasswordUtil;

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
		
		return new OperationResponse(success, code, responseData);
	}
	
	public static UserUpdateDTO selectUserProfileById(Integer userId) {
		String username = "";
		String displayName = "";
		String email = "";
		
		try {
			Connection conn = Db.getConnection();
			String sqlStatement = "SELECT username, email, display_name FROM user "
					+ "WHERE id = ? LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				username = rs.getString("username");
				displayName = rs.getString("display_name");
				email = rs.getString("email");
			} else {
				return null;
			}
			
			conn.close();
		} catch (SQLException e) {
	        System.out.println("SQLException at UserDAO.selectUserProfileById");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());

		}
		
		return new UserUpdateDTO(userId, username, displayName, email);
	}

	public static OperationResponse updateUserById(UserUpdateDTO userUpdateData) {
		boolean success = false;
		String code = "";
		String message = "";
		
		try {
			Connection conn = Db.getConnection();
			String sqlStatement = "UPDATE user "
					+ "SET username = ?, email = ?, display_name = ? "
					+ "WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			
			stmt.setString(1, userUpdateData.getUsername());
			stmt.setString(2, userUpdateData.getEmail());
			stmt.setString(3, userUpdateData.getDisplayName());
			stmt.setInt(4, userUpdateData.getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected == 1) {
				success = true;
				code = "SUCCESS_USER_UPDATE";
			} else {
				success = false;
				code = "ERR_USER_UPDATE_UNKNOWN";
			}
			
			conn.close();
		} catch(SQLException e) {
	        System.out.println("SQLException at UserDAO.updateUserById");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());

			success = false;
	        if (e.getErrorCode() == 1062) {
	            if (e.getMessage().contains("username")) {
	                code = "ERR_USER_UPDATE_DUPLICATE_USERNAME";
	            } else if (e.getMessage().contains("email")) {
	                code = "ERR_USER_UPDATE_DUPLICATE_EMAIL";
	            } else {
	            	code = "ERR_USER_UPDATE_UNKNOWN";
	            }
	        } else {
	        	code = "ERR_USER_UPDATE_UNKNOWN";
	        }
		}
		
		return new OperationResponse(success, code, message, null);
	}
	
	public static OperationResponse updateUserPasswordById(String oldPassword, String newPassword, Integer userId) {
		boolean success = false;
		String code = "";
		String message = "";
		
		try {
			Connection conn = Db.getConnection();
			String getOldPasswordSql = "SELECT password FROM user "
					+ "WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(getOldPasswordSql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String dbHash = rs.getString("password");
				
				boolean isCorrectPassword = PasswordUtil.verifyPassword(oldPassword, dbHash);

				if(isCorrectPassword) {
					success = true;
					code = "SUCCESS_VERIFY_PASSWORD";
					
					String newHash = PasswordUtil.hashPassword(newPassword);
					PreparedStatement newHashUpdate = conn.prepareStatement("UPDATE user "
							+ "SET password = ? WHERE id = ?");
					newHashUpdate.setString(1, newHash);
					newHashUpdate.setInt(2, userId);
					
					int rowsAffected = newHashUpdate.executeUpdate();
					
					if(rowsAffected == 1) {
						success = true;
						code = "SUCCESS_USER_PASSWORD_UPDATE";
					} else {
						success = false;
						code = "ERR_USER_PASSWORD_UPDATE_UNKNOWN";
					}
				} else {
					success = false;
					code = "ERR_USER_PASSWORD_UPDATE_INCORRECT_PASSWORD";
				}
			}
			
			conn.close();
		} catch(SQLException e) {
	        System.out.println("SQLException at UserDAO.updateUserPasswordById");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());

			success = false;
	        code = "ERR_USER_PASSWORD_UPDATE_UNKNOWN";
		}
		
		return new OperationResponse(success, code, message, null);
	}
}
