package com.silvercare.dao;

import java.sql.*;

import com.silvercare.dto.AdminDashboardDTO;
import com.silvercare.util.Db;

public class AdminDashboardDAO {
    public static AdminDashboardDTO selectMainDashboardData() {
        AdminDashboardDTO data = new AdminDashboardDTO();

        try {
        	Connection conn = Db.getConnection();

            Statement userCountStmt = conn.createStatement();
            ResultSet userCountRs = userCountStmt.executeQuery("SELECT COUNT(id) AS user_count FROM user "
            		+ "WHERE role_id != 2");
            if(userCountRs.next()) {
            	data.setUserCount(userCountRs.getInt("user_count"));
            }

            Statement bookingCountStmt = conn.createStatement();
            ResultSet bookingCountRs = bookingCountStmt.executeQuery("SELECT COUNT(id) AS booking_count "
                    + "FROM booking");
            if(bookingCountRs.next()) {
                data.setBookingCount(bookingCountRs.getInt("booking_count"));
            }

            Statement monthlyRevenueStmt = conn.createStatement();
            ResultSet monthlyRevenueRs = monthlyRevenueStmt.executeQuery("SELECT SUM(s.pricing) AS monthly_revenue "
                    + "FROM booking a "
                    + "JOIN service s ON a.service_id = s.id "
                    + "WHERE MONTH(a.starts_on) = MONTH(CURRENT_DATE()) "
                    + "AND YEAR(a.starts_on) = YEAR(CURRENT_DATE())");
            if(monthlyRevenueRs.next()) {
                data.setMonthlyIncome(monthlyRevenueRs.getFloat("monthly_revenue"));
            }

            Statement topCategoryStmt = conn.createStatement();
            ResultSet topCategoryRs = topCategoryStmt.executeQuery("SELECT sc.name, COUNT(*) AS total_bookings "
                    + "FROM booking a "
                    + "JOIN service s ON a.service_id = s.id "
                    + "JOIN service_category sc ON s.category_id = sc.id "
                    + "GROUP BY sc.id, sc.name "
                    + "ORDER BY total_bookings DESC "
                    + "LIMIT 1");
            if(topCategoryRs.next()) {
                data.setTopServiceCategory(topCategoryRs.getString("name"));
            }
            
            conn.close();
        } catch(SQLException e) {
			System.out.println("SQLException at AdminDashboardDAO.selectMainDashboardData");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());
        } catch (Exception e) {
			System.out.println("Unknown Exception at AdminDashboardDAO.selectMainDashboardData");
			System.out.println(e.getMessage());
		}

        return data;
    }
}