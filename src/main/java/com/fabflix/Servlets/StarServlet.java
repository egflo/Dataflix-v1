package com.fabflix.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fabflix.Management.API;
import com.fabflix.Management.Movie;
import com.fabflix.Management.Star;
import com.fabflix.Search.Search;

/**
 * Servlet implementation class StarServlet
 */
public class StarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		//Get values from href
		String name = request.getParameter("star_name");
		String star_id = request.getParameter("star_id");
		
		try {
			
			//Intialize Search Class
			Search search = new Search();
			Connection connection = search.getConnection();
					
			//Execute Query
			Star star = search.searchStar(name, star_id, connection);

			ArrayList<Movie> movies = star.getStarredin();

			for(Movie movie: movies) {
				//Use API to update entries in database
				API.movie(movie.getMovieid(),connection);
			}

			connection.close();

			//Direct to results page and show results from query
			request.setAttribute("star", star);
	    	RequestDispatcher view = request.getRequestDispatcher("StarResult.jsp");
	        view.forward(request, response);  
						 
		}
		
		catch(Exception e)
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
