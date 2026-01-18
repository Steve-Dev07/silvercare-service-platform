package com.silvercare.dao;

import com.silvercare.dto.CartDTO;
import com.silvercare.util.Db;
import com.silvercare.util.OperationResponse;

import java.sql.*;
import java.util.*;

public class CartDAO {
    public static OperationResponse insertCartItem(String serviceName, int userId) {
    	boolean success = false;
    	String code = "";

        try {
            Connection conn = Db.getConnection();

            String sqlStatement ="INSERT INTO cart_item (user_id, service_id) "
            	    + "VALUES (?, (SELECT id FROM service WHERE name = ?))";
        	PreparedStatement stmt = conn.prepareStatement(sqlStatement);

        	stmt.setInt(1, userId);
        	stmt.setString(2, serviceName);
        	stmt.executeUpdate();
        	
        	success = true;
        	code = "SUCCESS_ADD_CART_ITEM";

            conn.close();
        } catch (SQLException e) {
        	success = false;
			code = "ERR_ADD_CART_ITEM_UNKNOWN";
        	
            System.out.println("SQLException at CartDAO.insertCartItem");
            System.out.println("SQL Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Message: " + e.getMessage());
        } catch(Exception e) {
			success = false;
			code = "ERR_ADD_CART_ITEM_UNKNOWN";

			System.out.println("Unknown Exception at CartDAO.insertCartItem");
			e.printStackTrace();
		}
        
        return new OperationResponse(success, code, null);
    }
    
    public static List<CartDTO> selectCartItemsByUserId(int userId) {
    	List<CartDTO> cartItemsList = new ArrayList<>();
    	
    	try {
    		Connection conn = Db.getConnection();
    		String sqlStatement = "SELECT c.id, s.name AS service_name, s.title AS description, "
                    + "s.price, sc.name AS service_category "
                    + "FROM service s "
                    + "JOIN service_category sc ON s.category_id = sc.id "
                    + "JOIN cart_item c ON c.service_id = s.id "
                    + "WHERE c.user_id = ?";
    		PreparedStatement stmt = conn.prepareStatement(sqlStatement);
    		
    		stmt.setInt(1, userId);
    		
    		ResultSet rs = stmt.executeQuery();
    		
            while (rs.next()) {
                int id = rs.getInt("id");
                String serviceName = rs.getString("service_name");
                String serviceCategory = rs.getString("service_category");
                String description = rs.getString("description");
                double price = rs.getDouble("price");

                cartItemsList.add(new CartDTO(id, serviceName, serviceCategory, description, price));
            }
            
            conn.close();
    	} catch (SQLException e) {
            System.out.println("SQLException at CartDAO.selectCartItemsByUserId");
            System.out.println("SQL Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Message: " + e.getMessage());
    	}
    	
    	return cartItemsList;
    }
    
    public static OperationResponse deleteCartItemById(int cartItemId) {
    	boolean success = false;
    	String code = "";

        try {
            Connection conn = Db.getConnection();

            String sqlStatement ="DELETE FROM cart_item WHERE id = ?";
        	PreparedStatement stmt = conn.prepareStatement(sqlStatement);

        	stmt.setInt(1, cartItemId);
        	stmt.executeUpdate();
        	
        	success = true;
        	code = "SUCCESS_DELETE_CART_ITEM";

            conn.close();
        } catch (SQLException e) {
        	success = false;
			code = "ERR_DELETE_CART_ITEM_UNKNOWN";
        	
            System.out.println("SQLException at CartDAO.deleteCartItemById");
            System.out.println("SQL Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Message: " + e.getMessage());
        } catch(Exception e) {
			success = false;
			code = "ERR_DELETE_CART_ITEM_UNKNOWN";

			System.out.println("Unknown Exception at CartDAO.deleteCartItemById");
			e.printStackTrace();
		}
        
        return new OperationResponse(success, code, null);
    }
}