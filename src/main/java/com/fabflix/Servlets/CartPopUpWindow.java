package com.fabflix.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fabflix.Management.*;
import com.fabflix.Search.*;

/**
 * Servlet implementation class CartPopUpWindow
 */
public class CartPopUpWindow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartPopUpWindow() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String movieid = request.getParameter("movieid");

			//From session get the users cart data
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("User");
			Cart cart = user.getCart();
			
			Search search = new Search();
			Connection connection = search.getConnection();

			//LinkedHashMap<String, Movie> results = search.searchMovie(movieid, null, connection);
			//Movie movie = results.get(movieid);
			Movie movie = search.searchMovie(movieid,connection);
			search.closeConnection(connection);

			//Add movie to users cart
			boolean status = cart.addItem(movie);

			String out = "";
			//True = Success
			if(status) {
				//Set the new cart as current cart
				user.setCart(cart);
				//Set modified user in sesssion
				session.setAttribute("User", user);

				HashMap<Movie,Integer> items = cart.getItems();
				int num_items = items.entrySet().stream()
						.mapToInt(entry -> entry.getValue())
						.sum();

				out = "";
				out += "<button id ='close_popup'>&#x2715;</button>";
				out += "<table>";
				out += "<tr>" +
						"<td><i id='check' class='fa fa-check'></i></td>" +
						"<td><img src=" + movie.getPoster() + "></td>" +
						"<td>" +
						"<b style='color:green;'> Added To Cart</b>" +
						"<b>Cart subtotal (" + num_items + " items) $" + cart.calculate_total() + "</b>" +
						"</td>" +
						"<td>" +
						"<span class='line_sep'></span>" +
						"</td>" +
						"<td>" +
						"<button class='popup_button' onclick='cart()' type='button'>Cart</button>" +
						"<button class='popup_button' onclick ='checkout()' type='button'>Proceed to Checkout</button>" +
						"</td>" +
						"</tr>";
				out += "</table>";
			}

			else {
				out = "";
				out += "<button id ='close_popup'>&#x2715;</button>";
				out += "<table>";
				out += "<tr>" +
						"<td><i id='error' class='fas fa-times'></i></td>" +
						"<td><img src=" + movie.getPoster() + "></td>" +
						"<td>" +
						"<b style='color:red;'> Unable To Cart</b>" +
						"<b>Max Quantity Exceeded (Max Qty:" + MyConstants.MAX_QTY + ") </b>" +
						"</td>" +
						"<td>" +
						"<span class='line_sep'></span>" +
						"</td>" +
						"<td>" +
						"<button class='popup_button' onclick='cart()' type='button'>Cart</button>" +
						"<button class='popup_button' onclick ='checkout()' type='button'>Proceed to Checkout</button>" +
						"</td>" +
						"</tr>";
				out += "</table>";
			}

	        response.setStatus(200);
	        response.getWriter().write(out);
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();

		}
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
