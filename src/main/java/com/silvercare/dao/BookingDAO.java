package com.silvercare.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.silvercare.dto.BookingDTO;
import com.silvercare.util.Db;

public class BookingDAO {
	public static List<BookingDTO> selectBookingsByUserId(int userId) {
		List<BookingDTO> bookingsList = new ArrayList<>();
		
		try {
			Connection conn = Db.getConnection();
			String sqlQuery = "SELECT s.name, sc.name AS category, b.appointment_on, b.status, s.price "
					+ "FROM service s "
					+ "JOIN service_category sc ON s.category_id = sc.id "
					+ "JOIN booking b ON b.service_id = s.id "
					+ "WHERE b.user_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String service = rs.getString("name");
				String category = rs.getString("category");
				String appointmentTime = rs.getString("appointment_on");
				String status = rs.getString("status");
				double price = rs.getFloat("price");
				
				bookingsList.add(new BookingDTO(service, category, appointmentTime, status, price));
			}
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException at BookingDAO.selectBookingsByUserId");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unknown Exception at BookingDAO.selectBookingsByUserId");
			System.out.println(e.getMessage());
		}
		
		return bookingsList;
	}
}