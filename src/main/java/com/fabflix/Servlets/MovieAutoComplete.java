package com.fabflix.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.sql.*;
import java.util.List;

import com.fabflix.Search.Search;

/**
 * Servlet implementation class MovieAutoComplete
 */

@WebServlet(name = "MovieAutoComplete", value = "/MovieAutoComplete")
public class MovieAutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieAutoComplete() {
        super();

    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get terms typed inside searchbox
			String tokens = request.getParameter("term");
			String search_type = request.getParameter("search_type");

			//String[] tokens = query.split("\\s+");
			Search search = new Search();
			Connection connection = search.getConnection();
			List<String> data = search.autoCompleteMovies(search_type, tokens, connection);

			String gsonData = new Gson().toJson(data);
            response.getWriter().write(gsonData);
			
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
