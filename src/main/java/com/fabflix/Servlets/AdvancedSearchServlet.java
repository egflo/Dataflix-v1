package com.fabflix.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.fabflix.Management.Movie;
import com.fabflix.Search.Search;

/**
 * Servlet implementation class AdvancedSearchServlet
 */

@WebServlet(name = "AdvancedSearchServlet", value = "/AdvancedSearchServlet")
public class AdvancedSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvancedSearchServlet() {
        super();
     
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
			HttpSession session = request.getSession(true);
			String referer = request.getHeader("Referer");
			Search search = new Search();

			Set<Entry<String, String[]>> map = request.getParameterMap().entrySet();

			//Process form or leave it as an empty string ""
			String query = "";
			ArrayList<String> paramValues = new ArrayList<String>();

			for (Entry<String, String[]> entry : map) {
				String param = entry.getKey();
				String[] values = entry.getValue();
				String value = values[0];

				if(value.trim().length() != 0)
				{
					if(param.equals("title")){
						query += " movies.title =? AND";
						paramValues.add(value);
					}
					if(param.equals("year")){
						query += " movies.year =? AND";
						paramValues.add(value);
					}
					if(param.equals("director")){
						query += " movies.director =? AND";
						paramValues.add(value);
					}
					if(param.equals("name")){
						query += " stars.name =? AND";
						paramValues.add(value);
					}
					if(param.equals("cast_year")){
						query += " stars.birthYear =? AND";
						paramValues.add(value);
					}

					if(param.equals("genre")){
						query += " genres.name =? AND";
						paramValues.add(value);
					}
				}
			}

			query = query.substring(0, query.length() - 3);
		
		////////////////MOVIE QUERY PROCESSING////////////////////
		
		//If request is empty and was sent by AdvanceSearch.jsp
		//User was searching for a movie not sorting by title etc.
		//Did not put anything in the form.
		if(query.isEmpty() && referer.contains("AdvancedSearch.jsp")){
	    	 String message = "Error: Nothing was Entered. Please try again.";
	    	 request.setAttribute("message", message);
	    	 
	    	 RequestDispatcher view = request.getRequestDispatcher("AdvancedSearch.jsp");
	         view.forward(request, response);
		}
		
		//User was on movie result page and clicked on sorting/next/#ofresults
		else {
			//Initial Values
			String limit = "5";
			String offset = "0";

			Connection connection = null;
			try {
				connection = search.getConnection();
				LinkedHashMap<String, Movie> movies = search.searchAdvancedMovie(query,paramValues,limit, offset, null, connection);

				ArrayList<Movie> movie_list = new ArrayList<Movie>(movies.values());

				//Set results for JSP
				String message = "Advanced Search Results";
				request.setAttribute("message",message);
				request.setAttribute("results", movie_list);
				request.setAttribute("servlet", "SearchServlet");

				RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
				view.forward(request, response);

			}
			catch (Exception e) {
				e.printStackTrace();
			}

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
