package com.fabflix.Servlets;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


import com.fabflix.Management.Movie;
import com.fabflix.Management.User;
import com.fabflix.Management.VerifyUtils;
import com.fabflix.Search.Search;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try {
			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

			// Verify CAPTCHA.
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (!valid) {
				
				 //Message user with error
		    	 String message = "reCAPTCHA is Invalid";
		    	 request.setAttribute("message", message);
		    	 
		    	 RequestDispatcher view = request.getRequestDispatcher("login.jsp");
		         view.forward(request, response); 
		         
		         return;
			}
										
			// Read form fields
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        //String md5password = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);	      	     	 
	        
			Search search = new Search();
			Connection connection = search.getConnection();

			//Search customer database
			//Returns: a user if found otherwise null (empty)
			User user = search.searchUser(email, password, connection);
			 
			//If user found, create a session
			if(user != null)
			{
				//Return an existing Session or Create a New one
				HttpSession session = request.getSession();

				//Set inactive time
				session.setMaxInactiveInterval(60*30);

				User session_user = (User)session.getAttribute("User");
			 	//Check if User object exists in session
			 	if(session.getAttribute("User") == null || session_user.getid() == null) {
					//If not, set attribute with created User.
					session.setAttribute("User", user);
			 	}
			 	else {
			 		//Use existing User object in session
			 		session.setAttribute("User", session.getAttribute("User"));
			 	}

				Cookie ck[] = request.getCookies();

			 	for(Cookie c: ck) {
			 		if(c.getName().equals("cart_" + user.getid()) ) {
			 			String cart = URLDecoder.decode(c.getValue(),"utf-8");
						Gson gson = new Gson();
						Type item = new TypeToken<Map<String,Integer>>() {}.getType();
						Map<String,Integer> object = gson.fromJson(cart,item);

						HashMap<Movie,Integer> items = new HashMap<Movie,Integer>();
						for(Map.Entry<String,Integer> entry: object.entrySet()){
							String movie_id = entry.getKey();
							Movie movie = search.searchMovie(movie_id, connection);
							items.put(movie, entry.getValue());
						}

						user.getCart().setItems(items);
					}
				}

				search.closeConnection(connection);
				session.setAttribute("auth", true);
			 	session.setAttribute("uname", user.getfirst_name());

			 	//Direct to Main Page
				response.sendRedirect("IndexServlet");
		    	 
			}
		     //Otherwise notify user of invalid email/password
		     else {
		     	//Message user with error
				String message = "Invalid email/password";
				request.setAttribute("message", message);
		    	 
				RequestDispatcher view = request.getRequestDispatcher("login.jsp");
				response.sendRedirect("login.jsp");
				//view.forward(request, response);
		     }
		}
		
		catch (Exception e) {
			response.getWriter().write(e.toString());
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
