<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.silvercare.dto.ServiceDTO" %>
<%@ page import="com.silvercare.controller.ServiceController" %>
<%@ page import="com.silvercare.dto.CaregiverDTO" %>
<%@ page import="com.silvercare.controller.CaregiverController" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- includes bootstrap links -->
<%@ include file="./components/header-config.html" %>
<title>SilverCare | Service</title>
</head>
<body>
	<!-- includes dynamic client navBar.jsp -->
	<jsp:include page="./components/navBar.jsp"></jsp:include>

	<%
		// find serviceDetails by request parameter "name"
		String serviceName = request.getParameter("name");
		ServiceDTO serviceDetails = ServiceController.getServiceDetailsByName(serviceName);
	
		// set values for service details to use in expression tags
		String name = serviceDetails.getName();
		String title = serviceDetails.getTitle();
		String description = serviceDetails.getDescription();
		double price = serviceDetails.getPrice();
		String duration = serviceDetails.getDurationStr();
		String createdTime = serviceDetails.getCreatedTime();
		String updatedTime = serviceDetails.getLastUpdatedTime();
		int imgIndex = serviceDetails.getImgIndex();
	%>
	
    <%
        // checks cart functions were done before refresh and check the cartUpdateSuccess session attribute
        Boolean cartUpdateSuccess = (Boolean) session.getAttribute("cartUpdateSuccess");

        // initial notification toast properties
        String notificationColor = "#1D3142";
        String notificationMessage = "";
        String notificationTitle = "";
        String toastVisibility = "d-none";

        // proceeds if cart update was done before and redirected from AddCartItemServlet
        if (cartUpdateSuccess != null) {
            String message = (String) session.getAttribute("message");
            // makes notification visible
            toastVisibility = "d-block";

            // displays notification depending on cartUpdateSuccess status
            if (cartUpdateSuccess) {
                notificationTitle = "Cart Update Successful";
                notificationColor = "#077307";
                notificationMessage = "<i class=\"bi bi-arrow-up-right-square-fill\"></i>&ensp;" + message
                	+ "<br>\n<a href='" + request.getContextPath() + "/cart.jsp'>Go to Cart</a>\n";

            } else {
                notificationTitle = "Cart Update Failed";
                notificationColor = "#FF0000";
                notificationMessage = "<i class=\"bi bi-exclamation-triangle-fill\"></i>&ensp;" + message;
            }

            // removes the attribute after deciding cart update status
            session.removeAttribute("cartUpdateSuccess");
        }
    %>

	<div class="container py-5">
	    <div class="row mb-5">
	        <div class="col-12 col-md-6">
	            <img src="./img/service-<%= imgIndex %>-banner.png" class="img-fluid rounded" alt="Service Image">
	        </div>
	        <div class="col-12 col-md-6">
	            <h4 id="serviceName"><b><i class="bi bi-box2-heart"></i>&emsp;<%= name %></b></h4>
	            <p id="serviceDescription" class="my-4">
					<%= description %>
	            </p>
	            <ul class="list-group list-group-flush mb-3">
	                <li class="list-group-item"><strong><i class="bi bi-box2-heart"></i>&emsp;<span id="serviceTitle"><%= title %></span></strong></li>
	                <li class="list-group-item"><strong><i class="bi bi-tags"></i>&emsp;Price:</strong> $ <span id="servicePrice"><%= price %></span></li>
	                <li class="list-group-item"><strong><i class="bi bi-stopwatch"></i>&emsp;Duration:</strong> <span id="serviceDuration"><%= duration %></span></li>
	                <li class="list-group-item"><strong><i class="bi bi-rocket-takeoff"></i>&emsp;Service Offered On:</strong> <span id="serviceCreated"><%= createdTime %></span></li>
	                <li class="list-group-item"><strong><i class="bi bi-box-arrow-in-up"></i>&emsp;Last Updated:</strong> <span id="serviceUpdated"><%= updatedTime %></span></li>
	            </ul>
	        </div>
	    </div>
	
	    <div class="row">
	        <div class="col-12">
	            <h2 class="mb-4">Available Caregivers</h2>
	        </div>
	        
	        <% 
	        	// lists caregivers by service name and generates card for each caregiver
	        	var caregiversList = CaregiverController.getCaregiversByServiceName(serviceName);
	        	for(CaregiverDTO caregiver : caregiversList) {
	        		out.print("<div class='col-12 col-md-6 col-lg-4 mb-4'>"
	        		          + "<div class='card h-100 shadow-sm'>"
	        		          + "<div class='card-body'>"
	        		          + "<h5 class='card-title text-success'><i class=\"bi bi-person-badge\"></i>&ensp;" + caregiver.getName() +"</h5>"
	        		          + "<p class='card-text'><strong><i class=\"bi bi-patch-check\"></i>&ensp;Qualifications:</strong>&ensp;" + caregiver.getQualifications() + "<br>"
	        		          + "<strong><i class=\"bi bi-calendar-check\"></i>&ensp;Service since:</strong>&ensp;" + caregiver.getJoinedDate() + "</p>"
	        		          + "</div>"
	        		          + "</div>"
	        		          + "</div>");
	        	}
	        %>
	        
			<%
				// if user is logged in, generates add service to cart button
			    Integer userId = (Integer) session.getAttribute("userId");
			    if (userId != null) {
			%>
			    <form action="./add-cart-item" method="post" class="text-center mt-3">
			        <input type="hidden" name="serviceName" value="<%= serviceName %>">
			        <button type="submit" class="btn btn-success py-2 px-3"><i class="bi bi-bag-heart"></i>&ensp;Add Service to Cart</button>
			    </form>
			<%
			    }
			%>	
	    </div>
	</div>
	
    <!-- notification toast for cart update status -->
	<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 1055;">
	    <div class="toast fade show <%= toastVisibility %>" role="alert" aria-live="assertive" aria-atomic="true">
	        <div class="toast-header">
	            <svg aria-hidden="true" class="bd-placeholder-img rounded me-2" 
	            	height="20" preserveAspectRatio="xMidYMid slice" width="20" xmlns="http://www.w3.org/2000/svg">
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

	<!-- includes static client footer template -->
    <%@ include file="./components/footer.html" %>
</body>
</html>