package com.fabflix.Servlets;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.fabflix.Management.Cart;
import com.fabflix.Management.Movie;
import com.fabflix.Management.User;
import com.google.gson.Gson;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute("User");

		Gson gson = new Gson();
		Cart cart = user.getCart();

		//Cookie Cart
		if(cart.getItems().size() > 0) {
			Map<String,Integer> movies = new HashMap<>();
			for(Map.Entry<Movie,Integer> item: cart.getItems().entrySet()){
				Movie movie = item.getKey();
				Integer qty = item.getValue();
				movies.put(movie.getMovieid(),qty);
			}

			String json_cart = gson.toJson(movies);
			String encode_cookie = URLEncoder.encode(json_cart,"utf-8");

			Cookie cookie = new Cookie("cart_" + user.getid(), encode_cookie);
			cookie.setMaxAge(2000);
			response.addCookie(cookie);
		}

		session.invalidate();
   	 	response.sendRedirect("login.jsp");
        //RequestDispatcher view = request.getRequestDispatcher("login.jsp");
        //view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
