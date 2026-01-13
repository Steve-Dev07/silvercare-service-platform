<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../components/header-config.html" %>
<title>SilverCare | Login</title>
</head>
<body style="background-color: #1D3142;">
	<style>
		#register-text:hover {
			cursor: pointer;
			text-decoration: underline;
		}
	</style>

	<%
		// checks login was done before refresh and check the loginSuccess session attribute
		Boolean loginSuccess = (Boolean) session.getAttribute("loginSuccess");
	
		// initial notification toast properties
		String notificationColor = "#1D3142";
		String notificationMessage = "";
		String notificationTitle = "";
		String toastVisibility = "d-none";

		if(loginSuccess != null) {
			String message = (String) session.getAttribute("message");
			// makes notification visible
			toastVisibility = "d-block";

			if(loginSuccess) {
				notificationTitle = "Login Successful";
				notificationColor = "#077307";
				notificationMessage = "<i class=\"bi bi-arrow-up-right-square-fill\"></i>&ensp;" + message;

				// script to redirect to homepage
				out.print("<script>");
				out.print("setTimeout(function() {");
				out.print("window.location.href = '" + request.getContextPath() + "/index.jsp';");
				out.print("}, 1500);");
				out.print("</script>");
			} else {
				notificationTitle = "Login Failed";
				notificationColor = "#FF0000";
				notificationMessage = "<i class=\"bi bi-exclamation-triangle-fill\"></i>&ensp;" + message;
			}

			session.removeAttribute("loginSuccess");
		}
	%>

    <div class="container mt-5">
        <div class="row justify-content-center align-items-center">
            <div class="col-md-6 col-lg-5 col-xl-4 mb-4">
                <div class="card p-4">
                    <h2 class="text-center">Login</h2>
                    <!-- login form element -->
                    <form action="<%= request.getContextPath() %>/verify-user-login" method="post">
                        <div class="mb-3">
                            <label for="text" class="form-label"><i class="bi bi-person-circle"></i>&ensp;Username</label>
                            <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label"><i class="bi bi-person-fill-lock"></i>&ensp;Password</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
                        </div>
                        <div style="height: 10px;"></div>
                        <button type="submit" class="btn btn-primary w-100">Login</button>
                        <p class="mt-3">New to SilverCare? <i id="register-text" style="color: blue;"
                        	onclick="window.location.href='./register.jsp'"
                        >Create an account</i></p>
                    </form>
                </div>
            </div>
        </div>
    </div>

	<!-- notification toast for login status -->
	<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 1055;">
	    <div class="toast fade show <%= toastVisibility %>" role="alert" aria-live="assertive" aria-atomic="true">
	        <div class="toast-header">
	            <svg aria-hidden="true" class="bd-placeholder-img rounded me-2" height="20" preserveAspectRatio="xMidYMid slice" width="20" xmlns="http://www.w3.org/2000/svg">
	                <rect width="100%" height="100%" fill="<%= notificationColor %>"></rect>
	            </svg>
	            <strong class="me-auto" style="color: <%= notificationColor %>"><%= notificationTitle %></strong>
	            <small class="text-body-secondary">Just now</small>
	            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
	        </div>
	        <div class="toast-body">
	            <%= notificationMessage %>
	        </div>
	    </div>
	</div>
</body>
</html>