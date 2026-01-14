<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.silvercare.controller.UserController" %>
<%@ page import="com.silvercare.dto.UserUpdateDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="./components/header-config.html" %>
<title>SilverCare | Profile</title>
</head>
<body>

	<!-- to make sure session is logged in -->
	<%@ include file="./components/sessionTimeout.jsp" %>
	<!-- include client navBar.jsp -->
	<jsp:include page="./components/navBar.jsp"></jsp:include>
	
	<%
		Integer sessionUserId = (Integer) session.getAttribute("userId");
		Integer sessionRoleId = (Integer) session.getAttribute("roleId");
		UserUpdateDTO currentUserProfileData = UserController.getUserProfileDataById(sessionUserId);
		
		String username = "";
		String displayName = "";
		String email = "";
		String role = "";
		
		if(sessionRoleId == 1) {
			role = "Customer";
		} else if (sessionRoleId == 2) {
			role = "Administrator";
		}
		
		if(currentUserProfileData != null) {
			username = currentUserProfileData.getUsername();
			displayName = currentUserProfileData.getDisplayName();
			email = currentUserProfileData.getEmail();
		}
	%>
	
	<%
		// checks register was done before refresh and check the registerSuccess session attribute
		Boolean updateSuccess = (Boolean) session.getAttribute("updateSuccess");

		// initial notification toast properties
		String notificationColor = "#1D3142";
		String notificationMessage = "";
		String notificationTitle = "";
		String toastVisibility = "d-none";

		if (updateSuccess != null) {
			String message = (String) session.getAttribute("message");
			// makes notification visible
			toastVisibility = "d-block";

			if (updateSuccess) {
				notificationTitle = "Update Successful";
				notificationColor = "#077307";
				notificationMessage = "<i class=\"bi bi-arrow-up-right-square-fill\"></i>&ensp;" + message;

				// script to redirect to homepage
				out.print("<script>");
				out.print("setTimeout(function() {");
				out.print("window.location.href = '" + request.getContextPath() + "/profile.jsp';");
				out.print("}, 2000);");
				out.print("</script>");
			} else {
				notificationColor = "#FF0000";
				notificationTitle = "Update Failed";
				notificationMessage = "<i class=\"bi bi-exclamation-triangle-fill\"></i>&ensp;" + message;
			}

			session.removeAttribute("updateSuccess");
		}
	%>
	
	<div class="container mt-5 mb-5">
		<div class = "row gx-5 justify-content-center">
			<div class="col-12 col-md-4 mb-4 mb-md-0">
			    <div class="card p-4 shadow-sm">
			        <div class="d-flex flex-column align-items-center text-center">
			            <div style="
			                width: 80px;
			                height: 80px;
			                border-radius: 50%;
			                background-color: #E0E0E0;
			                display: flex;
			                align-items: center;
			                justify-content: center;
			                font-size: 32px;
			                font-weight: 600;
			                color: #555;">
			                <%= (displayName != null && !displayName.isEmpty())
			                        ? displayName.substring(0,1).toUpperCase()
			                        : "U" %>
			            </div>
			
			            <h5 class="mt-3 mb-1"><%= displayName %></h5>
			            <p class="text-muted mb-2">@<%= username %></p>
			
			            <p class="text-muted mb-1">
			                <i class="bi bi-envelope-at-fill"></i>&ensp;<%= email %>
			            </p>
			
			            <p class="text-muted mb-0" style="font-size: 14px;">
			                <i class="bi bi-person-badge-fill"></i>&ensp;<%= role %>
			            </p>
			        </div>
			
			        <hr class="my-4">
			
			        <p class="text-muted mb-1" style="font-size: 14px;">
			            From this page, you can update your personal details and review your bookings.
			        </p>
			        <p class="text-muted mb-0" style="font-size: 13px;">
			            Keeping your information up to date helps us personalize your care and contact you when needed.
			        </p>
			    </div>
			</div>
			
			<div class="col-12 col-md-6">
			    <div class="card p-4 shadow-sm">
			
			        <!-- Update Profile -->
			        <form action="<%= request.getContextPath() %>/update-user-profile" method="post">
			            <h5 class="mb-3">
			                <i class="bi bi-info-circle-fill"></i>&ensp;Update Profile
			            </h5>
			
			            <div class="mb-3">
			                <label class="form-label">
			                    <i class="bi bi-person-circle"></i>&ensp;Username
			                </label>
			                <input type="text"
			                       class="form-control update-profile-input-field"
			                       name="username"
			                       id="username-field"
			                       value="<%= username %>"
			                       required>
			            </div>
			
			            <div class="mb-3">
			                <label class="form-label">
			                    <i class="bi bi-person-badge"></i>&ensp;Display Name
			                </label>
			                <input type="text"
			                       class="form-control update-profile-input-field"
			                       name="displayName"
			                       id="displayName-field"
			                       value="<%= displayName %>"
			                       required>
			            </div>
			
			            <div class="mb-3">
			                <label class="form-label">
			                    <i class="bi bi-envelope-at-fill"></i>&ensp;Email
			                </label>
			                <input type="email"
			                       class="form-control update-profile-input-field"
			                       name="email"
			                       id="email-field"
			                       value="<%= email %>"
			                       required>
			            </div>
			
			            <div class="d-flex gap-2 mb-4">
			                <button type="submit"
			                        id="update-profile-button"
			                        class="btn btn-primary w-50"
			                        disabled>
			                    Update Profile
			                </button>
			            </div>
			        </form>
			
			        <hr class="my-4">
			
			        <!-- Change Password -->
			        <form id="updatePasswordForm"
			              action="<%= request.getContextPath() %>/update-user-password"
			              method="post">
			
			            <h5 class="mb-3">
			                <i class="bi bi-shield-lock-fill"></i>&ensp;Change Password
			            </h5>
			
			            <div class="mb-3">
			                <label class="form-label">Current Password</label>
			                <input type="password"
			                       class="form-control update-password-input-field"
			                       id="currentPassword"
			                       name="currentPassword"
			                       required
			                       pattern=".{8,}"
			                       title="Password must be at least 8 characters long">
			            </div>
			
			            <div class="mb-3">
			                <label class="form-label">New Password</label>
			                <input type="password"
			                       class="form-control update-password-input-field"
			                       id="newPassword"
			                       name="newPassword"
			                       required
			                       pattern=".{8,}"
			                       title="Password must be at least 8 characters long">
			            </div>
			
			            <div class="mb-3">
			                <label class="form-label">Confirm New Password</label>
			                <input type="password"
			                       class="form-control update-password-input-field"
			                       id="confirmPassword"
			                       name="confirmPassword"
			                       required
			                       pattern=".{8,}"
			                       title="Password must be at least 8 characters long">
			                <div id="passwordAlertText"
			                     class="text-danger mb-3"
			                     style="font-size: 13px;"></div>
			            </div>
			
			            <div class="d-flex gap-2 mb-4">
			                <button type="submit"
			                        id="update-password-button"
			                        class="btn btn-outline-primary w-50"
			                        disabled>
			                    Update Password
			                </button>
			            </div>
			        </form>
			
			        <script>
			        document.getElementById("updatePasswordForm").addEventListener("submit", function(e) {
			            const newPassword = document.getElementById("newPassword").value;
			            const confirmPassword = document.getElementById("confirmPassword").value;
			            const currentPassword = document.getElementById("currentPassword").value;
			
			            if (newPassword !== confirmPassword) {
			                e.preventDefault();
			                document.getElementById("passwordAlertText").innerHTML =
			                    `<i class="bi bi-exclamation-circle-fill"></i>&ensp;Passwords do not match.`;
			            }
			
			            if (newPassword === currentPassword) {
			                e.preventDefault();
			                document.getElementById("passwordAlertText").innerHTML =
			                    `<i class="bi bi-exclamation-circle-fill"></i>&ensp;New password must be different from current password.`;
			            }
			        });
			        </script>
			
			        <hr class="my-3">
			
			        <!-- Logout -->
			        <form action="<%= request.getContextPath() %>/logout-user" method="post">
			            <button type="submit" class="btn btn-danger w-50">
			                <i class="bi bi-box-arrow-left"></i>&ensp;Logout
			            </button>
			        </form>
			
			    </div>
			</div>
		</div>
	</div>

	<%@ include file="./components/footer.html" %>
	
	<script>
        // Enable "Update Profile" button when profile fields change
        const profileInputs = document.querySelectorAll(".update-profile-input-field");
        const updateProfileBtn = document.getElementById("update-profile-button");   
        
        profileInputs.forEach(input => {
            input.addEventListener("input", () => {
                const username = document.getElementById("username-field").value;
                const displayName = document.getElementById("displayName-field").value;
                const email = document.getElementById("email-field").value;
                
            	if(username == "<%= username %>" && email == "<%= email %>" && displayName == "<%= displayName %>") {
            		updateProfileBtn.disabled = true;
            	} else {
                	updateProfileBtn.disabled = false;
            	}
            });
        });

        // Enable "Update Password" button when any password fields change
        const passwordInputs = document.querySelectorAll(".update-password-input-field");
        const updatePasswordBtn = document.getElementById("update-password-button");
        passwordInputs.forEach(input => {
            input.addEventListener("input", () => {
                updatePasswordBtn.disabled = false;
            });
        });
    </script>
    
	<!-- notification toast for update status -->
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