package com.silvercare.dao;

import com.silvercare.dto.ServiceDTO;
import com.silvercare.util.Db;

import java.util.*;
import java.sql.*;

public class ServiceDAO {
	public static List<ServiceDTO> selectServicesByCategory(String category) {
		List<ServiceDTO> servicesList = new ArrayList<ServiceDTO>();
		
		try {
			Connection conn = Db.getConnection();
			String sqlStatement = "SELECT name, title, description, price, img_index, duration, created_on, last_updated_on "
					+ "FROM service "
					+ "WHERE category_id = ("
					+ "SELECT id FROM service_category WHERE "
					+ "name = ?)";
			
			PreparedStatement stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, category);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next() ) {
				String name = rs.getString("name");
				String title = rs.getString("title");
				String description = rs.getString("description");
				double price = rs.getFloat("price");
				int imgIndex = rs.getInt("img_index");
				int duration = rs.getInt("duration");
				String createdTime = rs.getString("created_on");
				String lastUpdatedTime = rs.getString("last_updated_on");
				servicesList.add(new ServiceDTO(name, title, description, price, imgIndex, duration, createdTime, lastUpdatedTime));
			}
			
			conn.close();
		} catch(SQLException e) {
	        System.out.println("SQLException at ServiceDAO.selectServicesByCategory");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());
		}
		
		return servicesList;
	}
}