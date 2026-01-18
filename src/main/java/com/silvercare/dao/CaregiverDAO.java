package com.silvercare.dao;

import com.silvercare.dto.CaregiverDTO;
import com.silvercare.util.Db;

import java.sql.*;
import java.util.*;

public class CaregiverDAO {
    public static List<CaregiverDTO> selectCaregiversByService(String service) {
        List<CaregiverDTO> caregiversList = new ArrayList<CaregiverDTO>();

        try {
            Connection conn = Db.getConnection();
            String sqlStatement = "SELECT c.name, c.qualifications, sc.joined_on "
                    + "FROM caregiver c "
                    + "JOIN service_caregiver sc ON c.id = sc.caregiver_id "
                    + "JOIN service s ON s.id = sc.service_id "
                    + "WHERE s.name = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, service);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String qualifications = rs.getString("qualifications");
                String joinedDate = rs.getString("joined_on");

                caregiversList.add(new CaregiverDTO(name, qualifications, joinedDate));
            }
            conn.close();
        } catch(SQLException e) {
			System.out.println("SQLException at CaregiverDAO.selectCaregiversByService");
	        System.out.println("SQL Error Code: " + e.getErrorCode());
	        System.out.println("SQL State: " + e.getSQLState());
	        System.out.println("SQL Message: " + e.getMessage());
        } catch (Exception e) {
			System.out.println("Unknown Exception at CaregiverDAO.selectCaregiversByService");
			System.out.println(e.getMessage());
		}

        return caregiversList;
    }
}