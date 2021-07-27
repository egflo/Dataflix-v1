package com.fabflix.Search;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


import com.fabflix.Management.*;


import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Search {
	//Set true for connection pooling. False otherwise.
	public final static boolean CONNECTION_POOLING = false;
	
	public Search() {
		super();
	}
	
	public Connection getConnection() throws Exception 
	{
	    
    	// the following few lines are for connection pooling
	    // Obtain our environment naming context
		if(CONNECTION_POOLING)
		{
	    	Context initCtx = new InitialContext();
	        if (initCtx == null) System.out.println ("initCtx is NULL");
		    
	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        if (envCtx == null) System.out.println ("envCtx is NULL");
			
		    // Look up our data source
		    DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			if (ds == null)
				System.out.println ("ds is null.");
				Connection connection = ds.getConnection();
			
			if (connection == null)
	           System.out.println ("dbcon is null.");
			
	    	return connection;
		}
		
		else
		{
			// Incorporate mySQL driver
			Class.forName("com.mysql.cj.jdbc.Driver");
	        String USER = "root";
	        String PASSWORD = "12251225";
	        
	    	// Connect to the test database
	    	Connection connection = DriverManager.getConnection
					("jdbc:mysql://10.81.1.123:4406/moviedb?useSSL=false",USER, PASSWORD);
			return connection;
		}
	}

	public void closeConnection(Connection connection) throws Exception {
		connection.close();
	}

	public ArrayList<String> getGenres(Connection connection) throws SQLException
	{
		ArrayList<String> genres = new ArrayList<String>();
		String query = "SELECT * FROM genres";
		
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);

		//Place genre names in a list
		while(results.next())
		{
			genres.add(results.getString(2));
		}
		
		results.close();
		statement.close();

		//Return genre list
		return genres;
		
	}

	public Map<String,Movie> getMovieRatings(ArrayList<Object> list, Connection connection) throws SQLException
	{
		// Prepare Query
		String query =
				"SELECT "
						+"movies.id as movie_id,"
						+"movies.title as movie_title,"
						+"movies.year as movie_year,"
						+"ratings.rating as rating,"
						+"ratings.numVotes as votes"
						+" FROM movies"
						+" INNER JOIN ratings"
						+" ON movies.id = ratings.movieId"
						+" WHERE ";

		for(Object id : list) {
			query += "movies.id = '" + (String)id + "' OR ";
		}

		String query_format = query.substring(0, query.length()-3);
		Statement statement = connection.createStatement();

		//Execute Query
		ResultSet result = statement.executeQuery(query_format);

		Map<String,Movie> movies = new HashMap<>();

		while(result.next())
		{
			Movie movie = new Movie();
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setYear(result.getString("movie_year"));
			movie.setRating(result.getString("rating"));
			movie.setVotes(result.getString("votes"));
			movies.put(result.getString("movie_id"), movie);

		}

		result.close();
		statement.close();

		return movies;
	}

	public ArrayList<Movie> getIndexMovieList(Connection connection) throws SQLException
	{
		// Prepare Queries
		String ratings_query =
				"SELECT "
					+"movies.id as movie_id,"
					+"movies.title as movie_title,"
					+"movies.year as movie_year,"
					+"movies.director as movie_director,"
					+"movies.poster as movie_poster,"
					+"ratings.rating as rating,"
					+"ratings.numVotes as votes"
					+" FROM movies"
					+" INNER JOIN ratings"
					+" ON movies.id = ratings.movieId"
					+" ORDER BY votes DESC LIMIT 5";

		String sellers_query =
				"SELECT "
					+"movies.id as movie_id,"
					+"movies.title as movie_title,"
					+"movies.year as movie_year,"
					+"movies.director as movie_director,"
					+"movies.poster as movie_poster,"
					+"COUNT(movies.id) as orders,"
					+"ratings.rating as rating,"
					+"ratings.numVotes as votes"
					+" FROM movies"
					+" INNER JOIN sales"
					+" ON movies.id = sales.movieId"
					+" INNER JOIN ratings"
					+" ON movies.id = ratings.movieId"
					+" GROUP BY movies.id, ratings.rating, ratings.numVotes"
					+" ORDER BY orders DESC LIMIT 5";


		Statement statement = connection.createStatement();

		//Execute Query
		ResultSet result = statement.executeQuery(ratings_query);

		//HashSet<Movie> movies = new HashSet<Movie>();
		ArrayList<Movie> movies = new ArrayList<Movie>();
		while(result.next())
		{
			Movie movie = new Movie();
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setYear(result.getString("movie_year"));
			movie.setDirector(result.getString("movie_director"));
			movie.setPoster(result.getString("movie_poster"));
			movie.setRating(result.getString("rating"));
			movie.setVotes(result.getString("votes"));
			movies.add(movie);
		}

		result = statement.executeQuery(sellers_query);

		while(result.next())
		{
			Movie movie = new Movie();
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setYear(result.getString("movie_year"));
			movie.setDirector(result.getString("movie_director"));
			movie.setPoster(result.getString("movie_poster"));
			movie.setRating(result.getString("rating"));
			movie.setVotes(result.getString("votes"));
			movies.add(movie);
		}

		result.close();
		statement.close();

		return movies;
	}

	public User searchAccount(String user_id, Connection connection) throws SQLException {
		//Returns full account details (cc_id, email, address) etc
		String query =
				"SELECT "
						+ "customers.id as user_id,"
						+ "customers.firstName as first_name,"
						+ "customers.lastName as last_name,"
						+ "customers.email as email,"
						+ "customers.password as password,"
						+ "customers.ccId as cc_id,"
						+ "customers.address as address,"
						+ "customers.unit as unit,"
						+ "customers.city as city,"
						+ "customers.state as state,"
						+ "customers.postcode as postcode,"
						+ "creditcards.firstName as cc_first_name,"
						+ "creditcards.lastName as cc_last_name,"
						+ "creditcards.expiration as cc_expiration,"
						+ "sales.id as sale_id,"
						+ "sales.customerId as sale_customer_id,"
						+ "sales.movieId as sale_movie_id,"
						+ "sales.saleDate as sale_date,"
						+ "sales.customerId as sale_customer_id,"
						+ "movies.id as movie_id,"
						+ "movies.title as movie_title,"
						+ "movies.poster as movie_poster,"
						+ "movies.year as movie_year,"
						+ "sales_movie.list_price as movie_price,"
						+ "sales_movie.quantity as movie_qty"
						+ " FROM customers"
							+ " INNER JOIN creditcards ON creditcards.id = customers.ccId"
							+ " LEFT JOIN (movies INNER JOIN sales ON movies.id = sales.movieId) ON sales.customerId = customers.id"
							+ " INNER JOIN sales_movie ON sales_movie.movieId = sales.movieId AND sales_movie.orderId = sales.id"
						+ " WHERE customers.id = ?";

		PreparedStatement statement = connection.prepareStatement(query);

		//Bind values into the parameters
		statement.setString(1, user_id);

		//Execute Query
		ResultSet result = statement.executeQuery();

		//Hold orders - unique identifier is the Date (change later)
		Map<String,Cart> orders = new HashMap<String,Cart>();

		//If User Exists then return User Object otherwise null
		User user = new User();
		Order order;

		while (result.next())
		{
			user.setid(result.getString("user_id"));
			user.setfirst_name(result.getString("first_name"));
			user.setlast_name(result.getString("last_name"));
			user.setEmail(result.getString("email"));
			user.setPassword(result.getString("password"));

			user.setAddress(result.getString("address"));
			user.setUnit(result.getString("unit"));
			user.setCity(result.getString("city"));
			user.setState(result.getString("state"));
			user.setPostcode(result.getString("postcode"));

			user.setcc_id(result.getString("cc_id"));
			user.setcc_fname(result.getString("cc_first_name"));
			user.setcc_lname(result.getString("cc_last_name"));
			user.setcc_expiration(result.getString("cc_expiration"));

			//Create Movie Class
			Movie movie = new Movie();
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setYear(result.getString("movie_year"));
			movie.setPoster(result.getString("movie_poster"));
			movie.setPrice(Float.valueOf(result.getString("movie_price")));

			order = new Order();
			order.setOrderId(result.getString("sale_id"));
			order.addItem(movie, Integer.parseInt(result.getString("movie_qty")));
			order.setSaleDate(result.getString("sale_date"));

			user.addOrder(order);
		}

		result.close();
		statement.close();

		return user;
	}

	public User searchUser(String email, String password, Connection connection) throws SQLException
	{
		// Prepare Query
		//ROM customers WHERE email = ? AND md5(password) = md5(?)"
		String query = 
				"SELECT " 
					+ "customers.id," 
					+ "customers.firstName,"
					+ "customers.lastName,"
					+ "customers.email"
				+" FROM customers WHERE email = ? AND password = ?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		 
		//Bind values into the parameters
		statement.setString(1, email);
		statement.setString(2, password);
		 
		//Execute Query
		ResultSet result = statement.executeQuery();

		//If User Exists then return User Object otherwise null
		User user = null;
		if(result.next())
		{
			user = new User();
			user.setid(result.getString(1));
		   	user.setfirst_name(result.getString(2));
		   	user.setlast_name(result.getString(3));
		   	user.setEmail(result.getString(4));
		}

		result.close();
		statement.close();
		return user;
	}
	
	public int recordSale(String customer_id, String movie_id, Connection connection) throws SQLException
	{
        String insertTableSQL = "INSERT INTO sales"
				+ "(customerid, movieid, sale_date) VALUES"
				+ "(?,?,?)";
		
        PreparedStatement statement = connection.prepareStatement(insertTableSQL);

		statement.setString(1, customer_id);
		statement.setString(2, movie_id);
		java.util.Date today = new java.util.Date();
		statement.setDate(3, new java.sql.Date(today.getTime()));

		int status = statement.executeUpdate();

		statement.close();
		connection.close();

		return status;
	}
	
	public Integer checkOutAuthentication(String cc_id, String first_name, String last_name, String expiration
			, Connection connection) throws SQLException {
		
		String query = "SELECT COUNT(*) FROM creditcards WHERE id =? AND first_name = ? AND last_name =? AND expiration =?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setString(1,cc_id);	
		statement.setString(2, first_name);	
		statement.setString(3, last_name);
		statement.setString(4, expiration);
		
		ResultSet result = statement.executeQuery();
		
		//Get Result
		result.next();
		int checkout_status = result.getInt(1);
		
		//Close Connections
		statement.close();
		result.close();
		connection.close();
		
		return checkout_status;
	}

	public List<String> autoCompleteMovies(String search_type, String tokens, Connection connection) throws SQLException
	{
		//Autcomplete the search bar -- Depending on the search type given
		Set<String> data = new HashSet<String>();

		String query;
		ResultSet results;
		Statement statement = connection.createStatement();

		if(search_type.equals("movie") || search_type.equals("all")) {

			query = String.format("SELECT id, title FROM movies WHERE MATCH(movies.title) " +
					"AGAINST('%s*' IN BOOLEAN MODE) LIMIT 7",tokens);

			results = statement.executeQuery(query);

			while(results.next())
			{
				data.add(results.getString("title"));
			}

			results.close();
		}

		if(search_type.equals("genre") || search_type.equals("all")) {

			query = String.format("SELECT id, name FROM genres WHERE MATCH(genres.name) " +
					"AGAINST('%s*' IN BOOLEAN MODE) LIMIT 7",tokens);

			results = statement.executeQuery(query);

			while(results.next())
			{
				data.add(results.getString("name"));
			}

			results.close();
		}

		if(search_type.equals("star") || search_type.equals("all")) {

			query = String.format("SELECT id, name FROM stars WHERE MATCH(stars.name) " +
					"AGAINST('%s*' IN BOOLEAN MODE) LIMIT 7",tokens);

			results = statement.executeQuery(query);

			while(results.next())
			{
				data.add(results.getString("name"));
			}

			results.close();
		}

		System.out.println(data.size());
		statement.close();
		connection.close();
		return new ArrayList<String>(data);
	}

	public Star searchStar(String name, String starid, Connection connection) throws SQLException
	{
		String query = 
			"SELECT "
				+"stars.id as star_id,"
				+"stars.name as name,"
			    +"stars.birthYear as star_year,"
			    +"stars.photo as star_photo,"
				+"stars.bio as star_bio,"
				+"stars.birthDetails as star_details,"
				+"stars.dod as star_dod,"
				+"movies.id as movie_id,"
			    +"movies.title as movie_title,"
				+"movies.year as movie_year,"
				+"movies.director as movie_director,"
				+"movies.plot as movie_plot,"
				+"movies.poster as poster,"
				+"movies.rated as movie_rated,"
				+"movies.runtime as movie_runtime,"
				+"movies.language as movie_language,"
				+"ratings.rating as rating,"
				+"ratings.numVotes as votes,"
				+"ratings.imdb as imdb,"
				+"ratings.metacritic as metacritic,"
				+"ratings.rottenTomatoes as rotten_tomatoes,"
				+"stars_in_movies.category as cast_category,"
				+"stars_in_movies.characters as cast_characters"
					+" FROM stars"
				+" INNER JOIN stars_in_movies" 
					+" ON stars.id = stars_in_movies.starId"
				+" INNER JOIN movies" 
					+" ON movies.id =  stars_in_movies.movieId"
				+" INNER JOIN ratings"
					+" ON movies.id = ratings.movieId"
			+" WHERE stars.id =?";

		//+" WHERE stars.id =? AND stars.name =?";

		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setString(1,starid);	
		//statement.setString(2, name);
	
		//Execute Query
		ResultSet result = statement.executeQuery();
			
		//Create Star Class 
		Star star = new Star();
		
		while(result.next())
		{
			star.setId(result.getString("star_id"));
			star.set_name(result.getString("name"));
			star.setdob(result.getString("star_year"));
			star.setPhoto(result.getString("star_photo"));
			star.setBio(result.getString("star_bio"));
			star.setDetails(result.getString("star_details"));
			star.setdod(result.getString("star_dod"));
			
			//Add Movie No Need to Worry about duplicates
			//Since it is a set

			Movie movie = new Movie();
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setPoster(result.getString("poster"));
			movie.setDirector(result.getString("movie_director"));
			movie.setYear(result.getString("movie_year"));
			movie.setRuntime(result.getString("movie_runtime"));
			movie.setRated(result.getString("movie_rated"));
			movie.setLanguage(result.getString("movie_language"));
			movie.setPlot(result.getString("movie_plot"));
			star.addMovie(movie);
		}
		
		result.close();
		statement.close();
		
		return star;
	}

	
	public LinkedHashMap<String, Movie> searchMovies(String type, String term, String Limit, String Offset, String Sort, Connection connection) throws SQLException
	{

		String query =
				"SELECT movies.id as movie_id, movies.title as movie_title, movies.year as movie_year, COUNT(sales.id) as sales, ratings.numVotes as votes" +
				" FROM movies" +
				" INNER JOIN sales ON movies.id = sales.movieId" +
				" INNER JOIN ratings ON movies.id = ratings.movieId" +
				" INNER JOIN genres_in_movies ON movies.id = genres_in_movies.movieId" +
				" INNER JOIN genres ON genres.id = genres_in_movies.genreId" +
				" INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movieId" +
				" INNER JOIN stars ON stars_in_movies.starId = stars.id";

		PreparedStatement statement;

		//-----------------------Type (Genre, Title, Rated) Query--------------------//

		//User searched for movie
		if(type.equals("movie")) {

			query += " WHERE movies.title LIKE ?";
		}

		else if(type.equals("star")) {
			query += " WHERE stars.name LIKE ?";
		}

		//User searched for genre
		else if(type.equals("genre")) {

			query += " WHERE genres.name = ?";
		}

		//User clicked on a rating button/link
		else if(type.equals("rating")) {

			query += " WHERE ratings.rated = ? ";
		}

		//User clicked on a year button/link
		else if(type.equals("year")) {

			query += " WHERE movies.year = ?";
		}

		//User searched for ALL
		else {
			query += " WHERE movies.title LIKE ? OR genres.name = ?";
		}

		//-----------------------Sorting Query--------------------//

		if(Sort != null) {

			if(Sort.equals("A_Z")) {
				query += " GROUP BY movie_id, votes ORDER BY movie_title ASC LIMIT " + Limit;
			}

			else if(Sort.equals("Z_A")) {
				query += " GROUP BY movie_id, votes ORDER BY movie_title DESC LIMIT " + Limit;
			}

			else if(Sort.equals("year_asc")) {
				query += " GROUP BY movie_id, votes ORDER BY movie_year ASC LIMIT " + Limit;
			}

			else if(Sort.equals("year_dsc")) {
				query += " GROUP BY movie_id, votes ORDER BY movie_year DESC LIMIT " + Limit;
			}

			else if(Sort.equals("best_seller")) {
				query += " GROUP BY movie_id, votes ORDER BY sales DESC LIMIT " + Limit;
			}

			else if(Sort.equals("most_pop")) {
				query += " GROUP BY movie_id, votes ORDER BY votes DESC LIMIT " + Limit;
			}

			else {
				query += " GROUP BY movie_id, votes ORDER BY sales DESC LIMIT " + Limit;
			}
		}

		//-----------------------LIMIT-OFFSET Query--------------------//

		else {

			query += " GROUP BY movie_id, votes ORDER BY movies.id LIMIT " + Limit;
		}

		if(Offset.length() > 0) {
			query += " OFFSET " + Offset;
		}

		LinkedHashMap<String, Movie> movies = new LinkedHashMap<String, Movie>();

		statement = connection.prepareStatement(query);

		if(type.equals("movie") || type.equals("star") || type.equals("all")) {

			statement.setString(1, '%' + term + '%');
		}

		else {
			statement.setString(1, term);
		}

		if(type.equals("all")){
			statement.setString(2,term);
		}

		//Loop over movie id from query and insert them into list
		ResultSet result = statement.executeQuery();

		while(result.next())
		{
			String id = result.getString("movie_id");
			API.movie(id,connection);

			//Prepare Query
			query =
				"SELECT "
					+"movies.id as movie_id,"
					+"movies.title as movie_title,"
					+"movies.year as movie_year,"
					+"movies.director as movie_director,"
					+"movies.poster as movie_poster,"
					+"movies.plot as movie_plot,"
					+"movies.rated as movie_rated,"
					+"movies.runtime as movie_runtime,"
					+"movies.language as movie_language,"
					+"movie_price.price as movie_price,"
					+"stars.id as star_id,"
					+"stars.name as star_name,"
					+"stars.photo as star_photo,"
					+"genres.id as genre_id,"
					+"genres.name as genre_name,"
					+"ratings.rating as rating,"
					+"ratings.numVotes as votes,"
					+"ratings.imdb as imdb,"
					+"ratings.metacritic as metacritic,"
					+"ratings.rottenTomatoes as rotten_tomatoes"
					+" FROM movies"
						+" INNER JOIN stars_in_movies"
							+" ON movies.id = stars_in_movies.movieId"
						+" INNER JOIN stars"
							+" ON stars_in_movies.starId = stars.id"
						+" INNER JOIN genres_in_movies"
							+" ON movies.id = genres_in_movies.movieId"
						+" INNER JOIN genres"
							+" ON genres.id = genres_in_movies.genreId"
						+" INNER JOIN ratings"
							+" ON movies.id = ratings.movieId"
						+" INNER JOIN movie_price"
							+" ON movies.id = movie_price.movieId"
						+" WHERE movies.id = ?";

			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			ResultSet result_movies = statement.executeQuery();

			Movie movie;
			//Get Results
			while(result_movies.next())
			{
				String movie_id = result_movies.getString("movie_id");

				//If movie already in list
				if(movies.containsKey(movie_id)) {
					//Get movie from HashMap
					movie = movies.get(movie_id);
				}

				else  {
					//Create Movie Class
					movie = new Movie();
					movie.setMovieId(movie_id);
					movie.setTitle(result_movies.getString("movie_title"));
					movie.setYear(result_movies.getString("movie_year"));
					movie.setDirector(result_movies.getString("movie_director"));
					movie.setPoster(result_movies.getString("movie_poster"));
					movie.setPlot(result_movies.getString("movie_plot"));
					movie.setRated(result_movies.getString("movie_rated"));
					movie.setRuntime(result_movies.getString("movie_runtime"));
					movie.setRating(result_movies.getString("rating"));
					movie.setVotes(result_movies.getString("votes"));
					movie.setIMDB(result_movies.getString("imdb"));
					movie.setMetacrtic(result_movies.getString("metacritic"));
					movie.setRottenTomatoes(result_movies.getString("rotten_tomatoes"));
					movie.setLanguage(result_movies.getString("movie_language"));
					movie.setPrice(result_movies.getFloat("movie_price"));

				}

				//Add star (Duplicates are removed)
				Star star = new Star();
				star.setId(result_movies.getString("star_id"));
				star.set_name(result_movies.getString("star_name"));

				//Add Genre (Duplicates are removed)
				Genre genre = new Genre();
				genre.setid(result_movies.getString("genre_id"));
				genre.setgenre(result_movies.getString("genre_name"));

				movie.addGenre(genre);
				movie.addStar(star);

				movies.put(movie_id, movie);
			}

			result_movies.close();
		}

		result.close();
		return movies;
	}

	public LinkedHashMap<String, Movie> searchAdvancedMovie(String search_query, ArrayList<String> values, String Limit, String Offset, String Sort, Connection connection) throws SQLException
	{
		String query =
				"SELECT movies.id as movie_id, movies.title as movie_title, movies.year as movie_year, COUNT(sales.id) as sales, ratings.numVotes as votes" +
						" FROM movies" +
						" INNER JOIN sales ON movies.id = sales.movieId" +
						" INNER JOIN ratings ON movies.id = ratings.movieId" +
						" INNER JOIN genres_in_movies ON movies.id = genres_in_movies.movieId" +
						" INNER JOIN genres ON genres.id = genres_in_movies.genreId" +
						" INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movieId" +
						" INNER JOIN stars ON stars_in_movies.starId = stars.id WHERE" + search_query;

		PreparedStatement statement;

		//-----------------------Sorting Query--------------------//

		if(Sort != null) {

			if(Sort.equals("A_Z")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY movie_title ASC LIMIT " + Limit;
			}

			else if(Sort.equals("Z_A")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY movie_title DESC LIMIT " + Limit;
			}

			else if(Sort.equals("year_asc")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY movie_year ASC LIMIT " + Limit;
			}

			else if(Sort.equals("year_dsc")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY movie_year DESC LIMIT " + Limit;
			}

			else if(Sort.equals("best_seller")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY sales DESC LIMIT " + Limit;
			}

			else if(Sort.equals("most_pop")) {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY votes DESC LIMIT " + Limit;
			}

			else {
				query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER sales DESC LIMIT " + Limit;
			}
		}

		//-----------------------LIMIT-OFFSET Query--------------------//

		else {

			query += " GROUP BY movies.id, movies.title, movies.year, ratings.numVotes ORDER BY movies.id LIMIT " + Limit;
		}

		if(Offset.length() > 0) {
			query += " OFFSET " + Offset;
		}

		LinkedHashMap<String, Movie> movies = new LinkedHashMap<String, Movie>();

		statement = connection.prepareStatement(query);

		for(int index = 0; index < values.size(); index++) {

			statement.setString(index+1, values.get(index));
		}


		//Loop over movie id from query and insert them into list
		ResultSet result = statement.executeQuery();

		while(result.next())
		{
			String id = result.getString("movie_id");
			API.movie(id,connection);

			//Prepare Query
			query =
					"SELECT "
							+"movies.id as movie_id,"
							+"movies.title as movie_title,"
							+"movies.year as movie_year,"
							+"movies.director as movie_director,"
							+"movies.poster as movie_poster,"
							+"movies.plot as movie_plot,"
							+"movies.rated as movie_rated,"
							+"movies.runtime as movie_runtime,"
							+"movies.language as movie_language,"
							+"stars.id as star_id,"
							+"stars.name as star_name,"
							+"stars.photo as star_photo,"
							+"genres.id as genre_id,"
							+"genres.name as genre_name,"
							+"ratings.rating as rating,"
							+"ratings.numVotes as votes,"
							+"ratings.imdb as imdb,"
							+"ratings.metacritic as metacritic,"
							+"ratings.rottenTomatoes as rotten_tomatoes"
							+" FROM movies"
							+" INNER JOIN stars_in_movies"
							+" ON movies.id = stars_in_movies.movieId"
							+" INNER JOIN stars"
							+" ON stars_in_movies.starId = stars.id"
							+" INNER JOIN genres_in_movies"
							+" ON movies.id = genres_in_movies.movieId"
							+" INNER JOIN genres"
							+" ON genres.id = genres_in_movies.genreId"
							+" INNER JOIN ratings"
							+" ON movies.id = ratings.movieId"
							+" WHERE movies.id = ?";

			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			ResultSet result_movies = statement.executeQuery();

			Movie movie;
			//Get Results
			while(result_movies.next())
			{
				String movie_id = result_movies.getString("movie_id");

				//If movie already in list
				if(movies.containsKey(movie_id)) {
					//Get movie from HashMap
					movie = movies.get(movie_id);
				}

				else  {
					//Create Movie Class
					movie = new Movie();
					movie.setMovieId(movie_id);
					movie.setTitle(result_movies.getString("movie_title"));
					movie.setYear(result_movies.getString("movie_year"));
					movie.setDirector(result_movies.getString("movie_director"));
					movie.setPoster(result_movies.getString("movie_poster"));
					movie.setPlot(result_movies.getString("movie_plot"));
					movie.setRated(result_movies.getString("movie_rated"));
					movie.setRuntime(result_movies.getString("movie_runtime"));
					movie.setRating(result_movies.getString("rating"));
					movie.setVotes(result_movies.getString("votes"));
					movie.setIMDB(result_movies.getString("imdb"));
					movie.setMetacrtic(result_movies.getString("metacritic"));
					movie.setRottenTomatoes(result_movies.getString("rotten_tomatoes"));
					movie.setLanguage(result_movies.getString("movie_language"));

				}

				//Add star (Duplicates are removed)
				Star star = new Star();
				star.setId(result_movies.getString("star_id"));
				star.set_name(result_movies.getString("star_name"));

				//Add Genre (Duplicates are removed)
				Genre genre = new Genre();
				genre.setid(result_movies.getString("genre_id"));
				genre.setgenre(result_movies.getString("genre_name"));

				movie.addGenre(genre);
				movie.addStar(star);

				movies.put(movie_id, movie);
			}

			result_movies.close();
		}

		result.close();
		return movies;
	}

	//Employee Methods
	public User searchEmployee(String email, String password, Connection connection) throws SQLException
	{
		// Prepare Query
		String query = 
				"SELECT * FROM employees WHERE email = ? AND md5(password) = md5(?)";
		
		PreparedStatement statement = connection.prepareStatement(query);
		 
		//Bind values into the parameters
		statement.setString(1, email);
		statement.setString(2, password);
		 
		//Execute Query
		ResultSet result = statement.executeQuery();
		
		//If User Exists then return User Object otherwise null
		User user = null;
		if(result.next())
		{
			user = new User();
			user.setEmail(result.getString(1));
			user.setPassword(result.getString(2));
			user.setlast_name(result.getString(3));
		}
		
		result.close();
		statement.close();
		connection.close();
		
		return user;
		
	}
	
	public Map<String,List<MetaData>> addMovie(String title, String year, String director, String poster, String trailer_url,
			String genre, String first_name, String last_name, String dob, String photo_url, Connection connection) throws SQLException
	{
		
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
				
	    java.sql.Date javaSqlDate = null;	
	    try
	    {
	    	javaSqlDate = java.sql.Date.valueOf(dob);
	    }

	    catch(Exception e)
	    {
	    	//No or Invalid Date 
	    	//Catch exception and pass it on
	    }
	    
		CallableStatement callStmt = connection.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		callStmt.setString(1, title);
		callStmt.setInt(2, Integer.parseInt(year));
		callStmt.setString(3, director);
		callStmt.setString(4, genre);
		callStmt.setString(5, first_name);
		callStmt.setString(6, last_name);
		callStmt.setDate(7, javaSqlDate);
		callStmt.setString(8, photo_url);
		callStmt.registerOutParameter(9, Types.INTEGER); //  current_movie_id 
		callStmt.registerOutParameter(10, Types.INTEGER); // current_genre_id 
		callStmt.registerOutParameter(11, Types.INTEGER); // current_star_id 
		callStmt.registerOutParameter(12, Types.INTEGER); // movie_count
		callStmt.registerOutParameter(13, Types.INTEGER); // star_count
		callStmt.registerOutParameter(14, Types.INTEGER); // genre_count
		callStmt.registerOutParameter(15, Types.INTEGER); // genres_in_movie_count
		callStmt.registerOutParameter(16, Types.INTEGER); // stars_in_movies_count
			
		callStmt.execute();
		
		String current_movie_id = callStmt.getString(9);
		String current_genre_id = callStmt.getString(10);
		String current_star_id = callStmt.getString(11);
		
		int movie_count = callStmt.getInt(12);
		int star_count= callStmt.getInt(13);
		int genre_count = callStmt.getInt(14);
		int stars_in_movies_count = callStmt.getInt(15);
		int genres_in_movie_count = callStmt.getInt(16);
		
		
		///MOVIES
		MetaData field_names = new MetaData();
		
		field_names.setVar1("Status");
		field_names.setVar2("ID");
		field_names.setVar3("Message");
		
		MetaData movie_data = new MetaData();
		List<MetaData> data = new ArrayList<MetaData>();
		
		movie_data.setVar2(current_movie_id);
		
		if(movie_count > 0)
		{
			movie_data.setVar1("UPDATE");
			movie_data.setVar3("Movie Exists. Movie Table Not Changed.");
		}
		
		else
		{
			movie_data.setVar1("INSERT");
			movie_data.setVar3("Movie Table Updated. Movie Inserted.");		
		}
		
		data.add(field_names);
		data.add(movie_data);
		metadata.put("movies", data);
		
		///////STARS
		MetaData star_data = new MetaData();
		star_data.setVar2(current_star_id);
			
		if(star_count > 0)
		{
			star_data.setVar1("DO NOTHING");
			star_data.setVar3("Star Exists. Cannot add Star to Star Table");
		}
		
		else
		{
			star_data.setVar1("INSERT");
			star_data.setVar3("Star Table Updated. Star has been added to Table.");
			
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(star_data);
		metadata.put("stars", data);
		
		
		/////GENRE
		MetaData genre_data = new MetaData();
		genre_data.setVar2(current_genre_id);;
		
		if(genre_count > 0)
		{
			genre_data.setVar1("DO NOTHING");
			genre_data.setVar3("Genre Exists. Cannot add Genre to Movie Database");		
		}
		else
		{
			genre_data.setVar1("INSERT");
			genre_data.setVar3("Genre Table Has been Updated");
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(genre_data);
		metadata.put("genres", data);
		
		
		/////////////STAR MOVIE LINK
		MetaData star_movie = new MetaData();
		star_movie.setVar2("N/A");
		
		if(stars_in_movies_count > 0)
		{
			star_movie.setVar1("DO NOTHING");
			star_movie.setVar3("Link Between Movie and Star Exists.");		
		}
		
		else
		{
			star_movie.setVar1("INSERT");
			star_movie.setVar3("Link Created Between Movie and Star");
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(star_movie);
		metadata.put("stars_in_movies", data);				
		
		MetaData genre_movie = new MetaData();
		genre_movie.setVar2("N/A");

		if(genres_in_movie_count > 0)
		{
			genre_movie.setVar1("DO NOTHING");
			genre_movie.setVar3("Linke Between Movie and Genre Exists.");		
		}
		
		else
		{
			genre_movie.setVar1("INSERT");
			genre_movie.setVar3("Link Created Between Movie and Genre");
		}
			
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(genre_movie);
		metadata.put("genres_in_movies", data);		
		
		callStmt.close();
		return metadata;
	}
	
	public int starExists(String[] star,Connection connection) throws SQLException
	{
		String firstName,lastName,dob;
		
		firstName = star[0];
		lastName = star[1];
		dob = star[2];
		    
		ResultSet results;
		PreparedStatement preparedStatement;
		
		if(dob.trim().isEmpty())
		{
			 
			String queryCount = "SELECT COUNT(*) FROM stars WHERE first_name = ? AND last_name = ? AND dob IS NULL";
			preparedStatement = connection.prepareStatement(queryCount);
			
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
				
			results = preparedStatement.executeQuery();
			
		}
		
		else
		{
			String queryCount = "SELECT COUNT(*) FROM stars WHERE first_name = ? AND last_name = ? AND dob = ?";
			preparedStatement = connection.prepareStatement(queryCount);
			
			java.sql.Date date = java.sql.Date.valueOf(dob);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			
			results = preparedStatement.executeQuery();
		    
			
		}

		
	    int count = 0;
	    if(results.next())
	    {
	    	count= results.getInt(1); 	
	    }
		
	    preparedStatement.close();
	    results.close();
	    
		return count;
	}
	
	public Map<String,List<MetaData>> addStar(String[] star, Connection connection) throws SQLException
	{
		String firstName,lastName,dob,photoURL;
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
		
		firstName = star[0];
		lastName = star[1];
		dob = star[2];
		photoURL = star[3];
		    
		String insertTableSQL;
		PreparedStatement preparedStatement;
		
		java.sql.Date date = null;
		if(dob.trim().isEmpty())
		{
			insertTableSQL = "INSERT INTO stars"
					+ "(first_name, last_name,dob,photo_url) VALUES"
					+ "(?,?,?,?)";
			
			preparedStatement = 
					connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			preparedStatement.setString(4, photoURL);
		}
		
		else
		{
			insertTableSQL = "INSERT INTO stars"
					+ "(first_name, last_name, dob, photo_url) VALUES"
					+ "(?,?,?,?)";
			
			preparedStatement = 
					connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			
			date = java.sql.Date.valueOf(dob);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			preparedStatement.setString(4, photoURL);
			
		}
		
		// execute insert SQL statement
		int rows = preparedStatement.executeUpdate();
		ResultSet id = preparedStatement.getGeneratedKeys();
		
		if(rows > 0 && id.next())
		{
			String query = "Select * FROM stars WHERE id = '"+ id.getInt(1)+"'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			List<MetaData> data = new ArrayList<MetaData>();
			MetaData meta = new MetaData();
			meta.setVar1("ID");
			meta.setVar2("First Name");
			meta.setVar3("Last Name");
			meta.setVar4("Date of Birth");
			meta.setVar5("Photo URL");
			
			data.add(meta);
			while(rs.next())
			{
				meta = new MetaData();
				
				meta.setVar1(rs.getString(1));
				
				String rs_fname = rs.getString(2);
				if(rs_fname == null || rs_fname.trim().isEmpty())
				{
					rs_fname = "N/A";
				}
				meta.setVar2(rs_fname);
				
				meta.setVar3(rs.getString(3));
				
				String rs_dob = rs.getString(4);
				if(rs_dob == null || rs_dob.trim().isEmpty())
				{
					rs_dob = "N/A";
				}
				
				String rs_photo = rs.getString(5);
				if(rs_photo == null || rs_photo.trim().isEmpty())
				{
					rs_photo = "N/A";
				}
				
				meta.setVar4(rs_dob);
				meta.setVar5(rs_photo);
				
				data.add(meta);
			}
			
			rs.close();
			id.close();
			statement.close();
			
			metadata.put("Stars (Rows Affected: " + rows + ")", data);
		}
	

		preparedStatement.close();
		connection.close();
		
		return metadata;
	}
	
	public Map<String,List<MetaData>> getMetaData(Connection connection) throws SQLException
	{
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
				
		while (rs.next()) 
		{
			List<MetaData> data = new ArrayList<MetaData>();
			
			String tableName = rs.getString(3);
			
			Statement select = connection.createStatement();
			ResultSet tableData = select.executeQuery("DESCRIBE " + tableName);
		
			MetaData table_names = new MetaData();
			table_names.setVar1("Field");
			table_names.setVar2("Type");
			table_names.setVar3("NULL");
			table_names.setVar4("Key");
			
			data.add(table_names);
			
			while(tableData.next())
			{
				String field = tableData.getString(1);
				String type = tableData.getString(2);
				String NULL = tableData.getString(3);
				String key = tableData.getString(4);
	    	  
				MetaData fields = new MetaData();
				fields.setVar1(field);
				fields.setVar2(type);
				fields.setVar3(NULL);
				fields.setVar4(key);
				
				data.add(fields);
			}
	      
			metadata.put(tableName, data);
			
			select.close();
			tableData.close();      
		}	
		
		rs.close();
		return metadata;
	}

	public Movie searchMovie(String id, Connection connection) throws SQLException
	{
		String query =
				"SELECT "
						+"movies.id as movie_id,"
						+"movies.title as movie_title,"
						+"movies.year as movie_year,"
						+"movies.director as movie_director,"
						+"movies.poster as movie_poster,"
						+"movies.plot as movie_plot,"
						+"movies.rated as movie_rated,"
						+"movies.runtime as movie_runtime,"
						+"movies.language as movie_language,"
						+"movie_price.price as movie_price,"
						+"stars.id as star_id,"
						+"stars.name as star_name,"
						+"stars.photo as star_photo,"
						+"genres.id as genre_id,"
						+"genres.name as genre_name,"
						+"ratings.rating as rating,"
						+"ratings.numVotes as votes,"
						+"ratings.imdb as imdb,"
						+"ratings.metacritic as metacritic,"
						+"ratings.rottenTomatoes as rotten_tomatoes"
						+" FROM movies"
						+" INNER JOIN stars_in_movies"
						+" ON movies.id = stars_in_movies.movieId"
						+" INNER JOIN stars"
						+" ON stars_in_movies.starId = stars.id"
						+" INNER JOIN genres_in_movies"
						+" ON movies.id = genres_in_movies.movieId"
						+" INNER JOIN genres"
						+" ON genres.id = genres_in_movies.genreId"
						+" INNER JOIN ratings"
						+" ON movies.id = ratings.movieId"
						+" INNER JOIN movie_price"
						+" ON movies.id = movie_price.movieId"
						+" WHERE movies.id =?";

		PreparedStatement statement;

		statement = connection.prepareStatement(query);
		statement.setString(1, id);


		//Execute Query
		ResultSet result = statement.executeQuery();

		Movie movie = new Movie();
		//Loop through query results
		while(result.next())
		{
			movie.setMovieId(result.getString("movie_id"));
			movie.setTitle(result.getString("movie_title"));
			movie.setYear(result.getString("movie_year"));
			movie.setDirector(result.getString("movie_director"));
			movie.setPoster(result.getString("movie_poster"));
			movie.setPlot(result.getString("movie_plot"));
			movie.setRated(result.getString("movie_rated"));
			movie.setRuntime(result.getString("movie_runtime"));
			movie.setRating(result.getString("rating"));
			movie.setVotes(result.getString("votes"));
			movie.setIMDB(result.getString("imdb"));
			movie.setMetacrtic(result.getString("metacritic"));
			movie.setRottenTomatoes(result.getString("rotten_tomatoes"));
			movie.setLanguage(result.getString("movie_language"));
			movie.setPrice(result.getFloat("movie_price"));


			//Add star (Duplicates are removed)
			Star star = new Star();
			star.setId(result.getString("star_id"));
			star.set_name(result.getString("star_name"));
			star.setPhoto(result.getString("star_photo"));

			//Add Genre (Duplicates are removed)
			//Create Genre Class
			Genre genre = new Genre();
			genre.setid(result.getString("genre_id"));
			genre.setgenre(result.getString("genre_name"));

			movie.addGenre(genre);
			movie.addStar(star);

		}
		result.close();
		statement.close();
		return movie;
	}
}

