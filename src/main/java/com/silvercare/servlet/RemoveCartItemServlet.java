package com.silvercare.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.silvercare.controller.CartController;
import com.silvercare.util.OperationResponse;

/**
 * Servlet implementation class DeleteCartItemServlet
 */
@WebServlet("/remove-cart-item")
public class RemoveCartItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveCartItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
		
		OperationResponse deleteCartItemResponse = CartController.removeCartItemById(cartItemId);
		HttpSession session = request.getSession();
		
		session.setAttribute("cartUpdateSuccess", deleteCartItemResponse.isSuccess());
		session.setAttribute("message", deleteCartItemResponse.getMessage());

		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

}
