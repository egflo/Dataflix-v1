package com.fabflix.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.fabflix.Management.Cart;
import com.fabflix.Management.Movie;
import com.fabflix.Management.User;
import com.fabflix.Search.Search;
import com.google.gson.Gson;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String title = request.getParameter("title");
		String movieid = request.getParameter("movieid");
		
		String action = request.getParameter("action");
		String quantity = request.getParameter("quantity");
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("User");
		Cart cart = user.getCart();

		try {
		//add/upadte/remove/get cart.
			if(action != null)
			{
				if (action.equals("add"))
				{
					Search search = new Search();
					Connection connection = search.getConnection();
					//LinkedHashMap<String,Movie> results = search.searchMovie(movieid, title, connection);
					Movie movie = search.searchMovie(movieid,connection);
					search.closeConnection(connection);

					//Add movie to users cart
					//cart.addItem(results.get(movieid));
					cart.addItem(movie);
				}

				else if (action.equals("Checkout"))
				{
					if(cart.getItems().size() == 0)
					{
						request.setAttribute("message", "Error: Cannot Checkout without any items in cart.");
					}

					else
					{
						RequestDispatcher view = request.getRequestDispatcher("Checkout.jsp");
						view.forward(request, response);
						return;
					}

				 }

				else if (action.equals("Empty Cart"))
				{
					 cart.emptyCart();
				}

				else if (action.equals("num_items")){

					HashMap<Movie,Integer> items = cart.getItems();
					int num_items = items.entrySet().stream()
							.mapToInt(entry -> entry.getValue())
							.sum();
					String num = new Gson().toJson(num_items);
					response.getWriter().write(num);
					return;
				}

				else {
					//Update button was pressed
					int number_of_items = Integer.parseInt(quantity);
					Movie movie = new Movie();

					movie.setMovieId(movieid);
					movie.setTitle(title);

					cart.setQuantity(movie, number_of_items);
				}

			}
				
			user.setCart(cart);
			session.setAttribute("User", user);

			request.setAttribute("user", user);
			request.setAttribute("results", cart.getItems());
			request.setAttribute("total", cart.calculate_total());
			RequestDispatcher view = request.getRequestDispatcher("Cart.jsp");
			view.forward(request, response);
		}
		
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
