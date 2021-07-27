package com.fabflix.Servlets;

import com.fabflix.Management.Movie;
import com.fabflix.Search.Search;
import com.google.gson.Gson;
import org.w3c.dom.NameList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@WebServlet(name = "RatingsServlet", value = "/RatingsServlet")
public class RatingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("data");
        System.out.println(data);

        Gson gson = new Gson();
        ArrayList<Object> id_list = gson.fromJson(data, ArrayList.class);

        try {
            Search search = new Search();
            Map<String,Movie> movie_list = search.getMovieRatings(id_list, search.getConnection());

            String gsonMovies = new Gson().toJson(movie_list);
            response.getWriter().write(gsonMovies);
        }

        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
