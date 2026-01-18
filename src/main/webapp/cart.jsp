<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.silvercare.dto.CartDTO" %>
<%@ page import="com.silvercare.controller.CartController" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- includes bootstrap links -->
<%@ include file="./components/header-config.html" %>
<title>SilverCare | Premium Caregiving Services</title>
</head>
<body>
    <!-- makes sure session is logged in -->
    <%@ include file="./components/sessionTimeout.jsp" %>
	<!-- includes dynamic client navBar.jsp -->
	<jsp:include page="./components/navBar.jsp"></jsp:include>
	
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
                notificationMessage = "<i class=\"bi bi-arrow-up-right-square-fill\"></i>&ensp;" + message;

                // script to redirect to cart page
                out.print("<script>");
                out.print("setTimeout(function() {");
                out.print("window.location.href = '" + request.getContextPath() + "/cart.jsp';");
                out.print("}, 2000);");
                out.print("</script>");
            } else {
                notificationTitle = "Cart Update Failed";
                notificationColor = "#FF0000";
                notificationMessage = "<i class=\"bi bi-exclamation-triangle-fill\"></i>&ensp;" + message;
            }

            // removes the attribute after deciding cart update status
            session.removeAttribute("cartUpdateSuccess");
        }
    %>

    <%
		Integer userId = (Integer) session.getAttribute("userId");
		var cartItemsList = CartController.getAllCartItemsByUserId(userId);
		double totalPrice = 0;
		
		for(CartDTO cartItem : cartItemsList) {
			totalPrice += cartItem.getPrice();
		}
    %>
    
	<div class="container py-4">
		<h3 class="mb-4"><b><i class="bi bi-bag-heart"></i>&ensp;Your Cart</b></h3>
		<%
		    if(cartItemsList.isEmpty()) {
		        out.print("<div class=\"alert alert-info\">Your cart is empty.</div>");
		    } else {
		        out.print("<div class=\"table-responsive mb-3\">"
					+ "<table class=\"table table-bordered align-middle\">"
					+ "<thead class=\"table-light\">"
					+ "<tr>"
					+ "<th style=\"width: 70px;\">#</th>"
					+ "<th>Service</th>"
					+ "<th>Category</th>"
					+ "<th>Description</th>"
					+ "<th>Price</th>"
					+ "<th style=\"width: 110px;\"></th>"
					+ "</tr>"
					+ "</thead>"
					+ "<tbody>");
		
		        for(CartDTO item : cartItemsList) {
		            out.print("<tr>"
						+ "<td>" + (cartItemsList.indexOf(item) + 1) + "</td>"
						+ "<td>" + item.getServiceName() + "</td>"
						+ "<td>" + item.getServiceCategory() + "</td>"
						+ "<td>" + item.getDescription() + "</td>"
						+ "<td>$ " + item.getPrice() + "</td>"
						+ "<td>"
						+ "<form action=\"" + request.getContextPath() + "/remove-cart-item\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"cartItemId\" value=\"" + item.getId() + "\">"
						+ "<button class=\"btn btn-danger btn-sm w-100\">Remove</button>"
						+ "</form>"
						+ "</td>"
						+ "</tr>");
		        }
		
		        out.print("</tbody>"
					+ "</table>"
					+ "</div>");
		    }
		%>
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