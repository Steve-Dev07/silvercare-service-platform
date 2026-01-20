package com.silvercare.controller;

import com.silvercare.dto.AdminDashboardDTO;
import com.silvercare.dao.AdminDashboardDAO;

public class AdminDashboardController {
	public static AdminDashboardDTO getAllDashboardAnalyticsData() {
		return AdminDashboardDAO.selectMainDashboardData();
	}
}