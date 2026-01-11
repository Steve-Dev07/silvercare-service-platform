package com.silvercare.dao;

import com.silvercare.dto.ServiceCategoryDTO;
import com.silvercare.util.Db;

import java.util.*;
import java.sql.*;

public class ServiceCategoryDAO {
	public static List<ServiceCategoryDTO> selectAllServiceCategories() {
		List<ServiceCategoryDTO> serviceCategoriesList = new ArrayList<>();
		
		try {
			Connection conn = Db.getConnection();
			Statement stmt = conn.createStatement();
			String sqlQuery = "SELECT id, name, description, img_index "
					+ "FROM service_category";

			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				int imgIndex = rs.getInt("img_index");
				
				serviceCategoriesList.add(new ServiceCategoryDTO(id, name, description, imgIndex));
			}
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException at ServiceCategoryDAO.selectAllServiceCategories");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unknown Exception at ServiceCategoryDAO.selectAllServiceCategories");
			System.out.println(e.getMessage());
		}
		
		return serviceCategoriesList;
	}
}