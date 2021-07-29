package com.fabflix.Management;

import com.fabflix.Search.Search;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class API {
    public static int movie(String movie_id, Connection connection)  {
        String API_KEY = "";
        int row_ratings, row_movie = 0;

        try {

            String count_query = "SELECT id, cached FROM movies WHERE id = ?";
            PreparedStatement count_stmt = connection.prepareStatement(count_query);

            count_stmt.setString(1,movie_id);
            ResultSet result = count_stmt.executeQuery();

            //Movie Exists
            if(result.next()) {
                //Is movie cached in database? 1:Yes, 0:No
                int cached = result.getInt("cached");
                if(cached > 1){
                    return 0; //0 updates to database (Movie Exists and in Database)
                }
            }

            // Create a neat value object to hold the URL
            URL url = new URL("http://www.omdbapi.com/?i="+movie_id+"&apikey="+API_KEY);

            // Open a connection(?) on the URL(??) and cast the response(???)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Now it's "open", we can set the request method, headers etc.
            conn.setRequestProperty("accept", "application/json");

            // This line makes the request
            InputStream inputStream = conn.getInputStream();

            //Convert to String JSON
            String content = IOUtils.toString(inputStream);

            JsonObject json = new JsonParser().parse(content).getAsJsonObject();
            String title = json.get("Title").getAsString();
            String year = json.get("Year").getAsString();
            String rated = json.get("Rated").getAsString();
            String runtime = json.get("Runtime").getAsString();
            String director = json.get("Director").getAsString();
            String plot = json.get("Plot").getAsString();
            String language = json.get("Language").getAsString();
            String poster = json.get("Poster").getAsString();
            String box_office = json.get("BoxOffice").getAsString();
            String production = json.get("Production").getAsString();
            String country = json.get("Country").getAsString();
            String awards = json.get("Awards").getAsString();
            String writer = json.get("Writer").getAsString();


            JsonArray ratings = json.get("Ratings").getAsJsonArray();
            HashMap<String,String> movie_ratings = new HashMap<>();
            for(int i = 0; i < ratings.size(); i++) {
                JsonElement source = ratings.get(i).getAsJsonObject().get("Source");
                JsonElement value = ratings.get(i).getAsJsonObject().get("Value");

                movie_ratings.put(source.getAsString(),value.getAsString());
            }

            String imdb = movie_ratings.get("Internet Movie Database");
            String rotten_tom = movie_ratings.get("Rotten Tomatoes");
            String metacritic = movie_ratings.get("Metacritic");


            String query = "UPDATE movies SET plot=?, rated=?, runtime=?, language=?, poster=?, writer=?, awards=?, boxOffice=?, production=?, country=?, cached=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,plot);
            statement.setString(2,rated);
            statement.setString(3,runtime);
            statement.setString(4,language);
            statement.setString(5,poster);
            statement.setString(6,writer);
            statement.setString(7,awards);
            statement.setString(8,box_office);
            statement.setString(9,production);
            statement.setString(10,country);
            //Movie is cached to database
            statement.setInt(11,1);

            statement.setString(12, movie_id);
            row_movie = statement.executeUpdate();

            query = "UPDATE ratings SET imdb=?, metacritic=?, rottenTomatoes=? WHERE movieId=?";
            statement = connection.prepareStatement(query);

            statement.setString(1,imdb);
            statement.setString(2,metacritic);
            statement.setString(3,rotten_tom);
            statement.setString(4,movie_id);

            row_ratings = statement.executeUpdate();
            //connection.commit();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return row_movie;
    }
}
