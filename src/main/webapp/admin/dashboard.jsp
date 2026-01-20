<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.silvercare.dto.AdminDashboardDTO" %>
<%@ page import="com.silvercare.controller.AdminDashboardController" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- includes bootstrap links -->
<%@ include file="../components/header-config.html" %>
<title>SilverCare | Admin Dashboard</title>
</head>
<body style="background-color: #F5F5F5;">
    <!-- makes sure session is logged in -->
    <%@ include file="../components/sessionTimeout.jsp" %>
    <!-- makes sure logged-in user is admin role -->
    <%@ include file="../components/verifyAdminStatus.jsp" %>

    <div class="container mt-5 mb-5">
    	<!-- page title -->
        <div class="row mb-4">
            <div class="col-12">
                <h2 style="font-weight: 700; color: #1D3142;">Admin Dashboard</h2>
            </div>
        </div>
        
        <%
        	// get dashboard data
        	AdminDashboardDTO data = AdminDashboardController.getAllDashboardAnalyticsData();
        
        	// set dashboard data to values for expression tags
        	int totalUsersCount = data.getUserCount();
        	int totalBookingCount = data.getBookingCount();
        	double currentMonthlyRevenue = data.getMonthlyIncome();
        	String topServiceCategory = data.getTopServiceCategory() == null ? "N.A" : data.getTopServiceCategory();
        %>

        <!-- business analytics widgets -->
        <div class="row g-4 mb-5">
        	<!-- tab showing total users -->
            <div class="col-md-3">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h6 class="text-muted"><i class="bi bi-person-check-fill"></i>&ensp;Total Users</h6>
                        <h4 style="font-weight:700; color:#1D3142;"><%= totalUsersCount %></h4>
                    </div>
                </div>
            </div>
			<!-- tab showing total bookings -->
            <div class="col-md-3">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h6 class="text-muted"><i class="bi bi-ui-checks"></i>&ensp;Total Bookings</h6>
                        <h4 style="font-weight:700; color:#1D3142;"><%= totalBookingCount %></h4>
                    </div>
                </div>
            </div>
			<!--  tab showing current monthly revenue -->
            <div class="col-md-3">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h6 class="text-muted"><i class="bi bi-cash"></i>&ensp;Current Monthly Revenue</h6>
                        <h4 style="font-weight:700; color:#1D3142;">$&nbsp;<%= currentMonthlyRevenue %></h4>
                    </div>
                </div>
            </div>
			<!-- tab showing top service category -->
            <div class="col-md-3">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h6 class="text-muted"><i class="bi bi-tag-fill"></i>&ensp;Top Service Category</h6>
                        <h4 style="font-weight:700; color:#1D3142;"><%= topServiceCategory %></h4>
                    </div>
                </div>
            </div>
        </div>

		<!-- admin management redirect buttons section -->
        <div class="row g-4">
        	<!-- description text -->
        	<div class="col-12">
        	    <h5 class="text-muted my-2">
                    Use the panels below to manage care services and registered users.
                </h5>
        	</div>
        	<!-- manage services tab -->
            <div class="col-md-6">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-box"></i>&ensp;Manage Services</h5>
                        <p class="card-text" style="font-size: 14px;">
                            Create, view, update, and remove caregiving services and pricing details.
                        </p>
                        <button class="btn"
                                style="background-color:#2C2C2C; color:white;"
                                onclick="window.location.href='<%= request.getContextPath() %>/services.jsp'">
                            Go to Services
                        </button>
                    </div>
                </div>
            </div>
			<!-- manage users tab -->
            <div class="col-md-6">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-person-circle"></i>&ensp;Manage Users</h5>
                        <p class="card-text" style="font-size: 14px;">
                            View and manage registered client accounts.
                        </p>
                        <button class="btn"
                                style="background-color:#2C2C2C; color:white;"
                                onclick="window.location.href='<%= request.getContextPath() %>/users.jsp'">
                            Go to Users
                        </button>
                    </div>
                </div>
            </div>
            <!-- manage booking appointments tab -->
            <div class="col-md-6">
                <div class="card shadow-sm" style="border-radius: 16px;">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-calendar-check"></i>&ensp;Manage Booking Appointments</h5>
                        <p class="card-text" style="font-size: 14px;">
                            View, edit, and approve customer booking appointments.
                        </p>
                        <button class="btn"
                                style="background-color:#2C2C2C; color:white;"
                                onclick="window.location.href='<%= request.getContextPath() %>/appointments.jsp'">
                            Go to Appointments
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>