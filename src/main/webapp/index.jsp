<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- includes bootstrap links -->
<%@ include file="./components/header-config.html" %>
<title>SilverCare | Premium Caregiving Services</title>
</head>
<body>
	<!-- includes dynamic client navBar.jsp -->
	<jsp:include page="./components/navBar.jsp"></jsp:include>
	
	<!-- first section: What We Do -->
    <section class="py-5">
        <div class="container">
            <div class="row align-items-center gy-4">
            	<!-- silvercare logo container -->
                <div class="col-md-4 text-center">
                    <img src="./img/silvercare-logo.png" 
                         alt="SilverCare Logo" 
                         class="img-fluid" 
                         style="max-width: 250px; border-radius: 20px;">
                </div>
                <!-- What We Do: title and description -->
                <div class="col-md-7">
                    <h2 class="fw-bold">What We Do</h2>
                    <p class="mt-3">
                        SilverCare provides compassionate and professional in-home assistance, offering a range
                        of personalized caregiver services tailored to the unique need of seniors and individuals
                        requiring support to maintain their independence and quality of life. Our dedicated team is
                        committed to delivering exceptional care, focusing on everything from daily living activities
                        and medication reminders to specialized companionship.
                    </p>
                </div>
            </div>
        </div>
    </section>

	<!-- second section: Our Services -->
    <section class="py-5 bg-white">
        <div class="container">
            <div class="row gy-4 align-items-center">
            	<!-- service category hero image container -->
                <div class="col-md-5 text-center">
                    <img src="./img/service-category-hero.png" 
                         alt="SilverCare Services" 
                         class="img-fluid rounded shadow">
                </div>
                <!-- Our Services: title and description -->
                <div class="col-md-6 mt-0 pt-0">
                    <h2 class="fw-bold mt-0 pt-0">Our Services - Your Satisfaction</h2>
                    <p class="mt-3">
                        We offer a wide variety of services designed to meet your needs and exceed your expectations. 
                        With a strong focus on quality and customer care, we ensure every client enjoys a smooth, 
                        satisfying experience from start to finish. Choose us for reliable service, exceptional value, 
                        and results you can trust.
                    </p>
                    <!-- Browse Services button: redirects to serviceCategories.jsp -->
                    <button style="
                        background-color: #2C2C2C;
                        color: white;
                        padding: 5px 30px 5px 30px;
                        border-radius: 10px;"
                        onclick="window.location.href='./serviceCategories.jsp'"
                    >
                        <i class="bi bi-search-heart"></i>&ensp;Browse Services
                    </button>
                </div>
            </div>
        </div>
    </section>

	<!-- includes static client footer template -->
    <%@ include file="./components/footer.html" %>
</body>
</html>