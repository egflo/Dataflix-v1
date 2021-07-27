package com.fabflix.Servlets;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.sql.*;
import java.util.LinkedHashMap;


import com.fabflix.Management.Movie;
import com.fabflix.Management.Star;
import com.fabflix.Search.Search;

/**
 * Servlet implementation class MoviePopUpWindow
 */
@WebServlet(name = "MoviePopUpWindow", value = "/MoviePopUpWindow")
public class MoviePopUpWindow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoviePopUpWindow() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String movieid = request.getParameter("movieid");

			Search search = new Search();
			Connection connection = search.getConnection();
			//LinkedHashMap<String, Movie> result = search.searchMovie(movieid, "", connection);
			//Movie movie = result.get(movieid);
			Movie movie = search.searchMovie(movieid,connection);
		
			//Keep Might need this
			Gson gson = new Gson();
			gson.toJson(movie); 	
			
			String starsout = "";
			int count = 0;
			for(Star star: movie.getStars())
			{
				if(count == movie.getStars().size() - 1)
				{
					starsout += star.getName();
				}
				else
				{
					starsout += star.getName() +", ";
				}
				count++;
			}
			
			String out = "";
			out += "<table>";
			out += "<tr><td>Movie ID: " + movie.getMovieid() + "</td></tr>";
			out += "<tr><td>Title: " + movie.getTitle() + "</td></tr>";
			out += "<tr><td>Year: " + movie.getYear() + "</td></tr>";
			out += "<tr><td>Director: " + movie.getDirector() + "</td></tr>";
			out += "<tr><td>Banner URL: <a href="+ movie.getPoster() +">Poster Link</a></td></tr>";
			out += "<tr><td>Trailer URL: <a href="+ movie.getTrailer_url()+">Trailer Link</a></td></tr>";
			out += "<tr><td>Stars: " + starsout + "</td></tr>";
			out += "</table>";

            response.setStatus(200);
            response.getWriter().write(out);
            search.closeConnection(connection);
		}
		
		catch(Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
