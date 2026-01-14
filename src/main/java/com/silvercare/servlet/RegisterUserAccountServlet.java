package com.silvercare.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.silvercare.controller.UserController;

/**
 * Servlet implementation class RegisterUserAccountServlet
 */
@WebServlet("/register-user")
public class RegisterUserAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUserAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String displayName = request.getParameter("displayName");
		String password = request.getParameter("password");
		
		var registerResponse = UserController.registerNewUser(username, email, displayName, password);
		var session = request.getSession();
		
		session.setAttribute("registerSuccess", registerResponse.isSuccess());
		session.setAttribute("message", registerResponse.getMessage());

		if(registerResponse.isSuccess()) {
			session.setAttribute("userId", ((Map<String, Object>) registerResponse.getData()).get("newUserId"));
			session.setAttribute("roleId", ((Map<String, Object>) registerResponse.getData()).get("roleId"));
			session.setAttribute("displayName", (displayName == null || displayName.isEmpty()) ? username : displayName);
		}
		
		response.sendRedirect(request.getContextPath() + "/register.jsp");
	}

}
