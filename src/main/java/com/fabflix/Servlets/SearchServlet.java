package com.fabflix.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fabflix.Management.API;
import com.fabflix.Management.Cart;
import com.fabflix.Management.Movie;
import com.fabflix.Management.User;
import com.fabflix.Search.Search;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.RequestDispatcher;




/**
 * Servlet implementation class SearchServlet
 */

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
				
		try
		{
			HttpSession session = request.getSession(true);
			//Type: Letter or Genre (For Search)
			String type = request.getParameter("type");
			//Term: 1,2,A,BC or Adventure, Horror
			String term = request.getParameter("term");

			System.out.println(type);
			System.out.println(term);

			Search search = new Search();
			Connection connection = search.getConnection();

			LinkedHashMap<String, Movie> movies;

			//User came from the index/main page
			if(type.equals("genre") || type.equals("all") || type.equals("movie") || type.equals("star")) {
				String parameters;

				//Initial Values
				String limit = "5";
				String offset = "0";

				movies = search.searchMovies(type, term, limit, offset, null, connection);
				
				//Get values from HashMap and return result to jsp page
				ArrayList<Movie> movie_list = new ArrayList<Movie>(movies.values());

				//Use API to update entries in database
				for(Movie movie: movie_list) {
					API.movie(movie.getMovieid(),connection);
				}

				//Store in session for future sorting
				//Default Values for Page Result limit, offset, and page No.
				session.setAttribute("limit", limit);
				session.setAttribute("offset", offset);
				session.setAttribute("data", movie_list);
				session.setAttribute("term", term);
				session.setAttribute("type", type);
				session.setAttribute("sort", null);


				//Set results for JSP  
				String message = "Search Results for " + term;
				request.setAttribute("message",message);
				request.setAttribute("results", movie_list);
				request.setAttribute("servlet", "SearchServlet");
		    	
				RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
		        view.forward(request, response);  
			}
			
			//User clicked on sort by/Show x results/prev/next
			else
			{
				String session_type = (String) session.getAttribute("type");
				String session_term = (String) session.getAttribute("term");
				String session_offset = (String)session.getAttribute("offset");
				String session_limit = (String)session.getAttribute("limit");
				String session_sort = (String)session.getAttribute("sort");

				ArrayList<Movie> movie_list = (ArrayList<Movie>) session.getAttribute("data");
				
				//If Movie List is empty do nothing and return
				if(movie_list.size() == 0)
				{
					request.setAttribute("message","Search Results (0 Movies Found)");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				}
				
				//Get 5,10,15,20 results
				//Keep results as they were stored
				else if(type.equals("limit"))
				{
					//Set new limit
					session.setAttribute("limit", term);

					//Only Limit is changed keep everything else the same
					movies = search.searchMovies(session_type, session_term, term, session_offset, session_sort, connection);

					//Get values from HashMap and return result to jsp page
					movie_list = new ArrayList<Movie>(movies.values());

					//Use API to update entries in database
					for(Movie movie: movie_list) {
						API.movie(movie.getMovieid(),connection);
					}

					connection.close();

					request.setAttribute("results", movie_list);
					request.setAttribute("servlet", "SearchServlet");
					
					request.setAttribute("message","Search Results");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response);
				}

				//User Click on the PREV or NEXT buttons
				else if (type.equals("page"))
				{

					int new_offset;
					//User Clicks Next Page
					if(term.equals("Next"))
					{
						//New offset => initial offset + limit
						new_offset = Integer.parseInt(session_offset) + Integer.parseInt(session_limit);
						if(movie_list.size() < Integer.parseInt(session_limit))
						{
							new_offset = Integer.parseInt(session_limit);
						}
					}
					
					//User Clicks Prev Page
					else
					{
						//New offset => initial offset - limit
						new_offset = Integer.parseInt(session_offset) - Integer.parseInt(session_limit);
						if(new_offset <= 0){
							new_offset = 0;
						}
					}

					session.setAttribute("offset", String.valueOf(new_offset));

					//Only offset is changed keep everything else the same
					movies = search.searchMovies(session_type, session_term, session_limit, String.valueOf(new_offset),null, connection);

					//Get values from HashMap and return result to jsp page
					movie_list = new ArrayList<Movie>(movies.values());

					//Set results for JSP 
					String message = "Search Results";;
					request.setAttribute("message",message);
					request.setAttribute("results", movie_list);
					request.setAttribute("servlet", "SearchServlet");

					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response);
					//response.sendRedirect(request.getContextPath() + "/SearchResults.jsp");
				}
				
				else
				{
					//Sorting Movies
					//Only sort is changed keep everything else the same
					movies = search.searchMovies(session_type, session_term, session_limit, session_offset, term, connection);

					//Get values from HashMap and return result to jsp page
					movie_list = new ArrayList<Movie>(movies.values());

					//Set the new sort attribute
					session.setAttribute("sort", term);
			
					//Send it back to JSP to show to user							
					request.setAttribute("message","Search Results (Sorted)");
					request.setAttribute("servlet", "SearchServlet");
					request.setAttribute("results", movie_list);
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				}
			}

			//From session get the users cart data
			User user = (User) session.getAttribute("User");
			Cart cart = user.getCart();

		}
		
		catch (Exception e) {
			response.getWriter().write(e.toString());
			e.printStackTrace();
		}

		//connection.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
