package com.fabflix.Management;

import java.util.ArrayList;
import java.util.HashSet;

public class Movie {
	private String movieid;
	private String title;
	private String year;
	private String rated;
	private String runtime;
	private String director;
	private String plot;
	private String poster;
	private String language;
	private String metacrtic;
	private String imdb_rating;
	private String rotten_tomatoes;

	//Not in Use
	private String trailer_url;

	private double rating;
	private String numVotes;
	private float price;
	
	private HashSet<Star> stars;
	private HashSet<Genre> genres;
	
	public Movie() {
		stars = new HashSet<Star>();
		genres = new HashSet<Genre>();
	}
	
	/** Set/Add Methods */
	public void setMovieId(String movieid) 
	{
		this.movieid = movieid;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setRated(String rated)
	{
		this.rated = rated;
	}

	public void setRuntime(String runtime)
	{
		this.runtime = runtime;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setDirector(String director)
	{
		this.director = director;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setIMDB(String rating) {
		this.imdb_rating = rating;
	}

	public void setMetacrtic(String rating) {
		this.metacrtic = rating;
	}

	public void setRottenTomatoes(String rating) {
		this.rotten_tomatoes = rating;
	}

	public void setRating(String rating) {
		double float_rating = Float.parseFloat(rating);
		double five_star_rating = float_rating * .5;
		double rounded = (double) Math.round(five_star_rating * 100) / 100;
		this.rating = rounded;
	}

	public void setVotes(String votes) {this.numVotes = votes;}

	public void setLanguage(String language) {this.language = language;}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}

	public void addStar(Star star) {
		stars.add(star);
	}
	
	public void addGenre(Genre genre) {
		genres.add(genre);
	}

	/** Accessor Methods */
	public String getMovieid() {
		return movieid;
	}
	
	public String getTitle() {
		return title;
	}

	public String getRated() {
		return rated;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getYear() {
		return year;
	}
	
	public String getDirector() {
		return director;
	}

	public String getPlot() {
		return plot;
	}

	public String getIMDB() {
		return imdb_rating;
	}

	public String getMetacrtic() {
		if(metacrtic == null) {
			return "0";
		}
		else {
			return metacrtic.split("/")[0];
		}
	}

	public String getRottenTomatoes() {
		return rotten_tomatoes;
	}

	public String getPoster() {
		return poster;
	}

	public String getVotes() {
		//Adapted from https://stackoverflow.com/questions/41859525/how-to-go-about-formatting-1200-to-1-2k-in-android-studio
		int number = Integer.parseInt(numVotes);
		if (number < 1000)
			return "" + number;
		int exp = (int) (Math.log(number) / Math.log(1000));
		return String.format("%.1f %c", number / Math.pow(1000, exp), "kMGTPE".charAt(exp-1));
	}

	public double getRating() {
		return rating;
	}

	public String getLanguage() {
		return language;
	}
	
	public String getTrailer_url() {
		return trailer_url;
	}

	public ArrayList<Star> getStars() {
		return new ArrayList<Star>(stars);
	}
	
	public ArrayList<Genre> getGenres() {
		return new ArrayList<Genre>(genres);
	}
	
	public float getPrice() {
		return price;
	}

	public String getPriceStr() {
		return String.format("%.2f", price);
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getMovieid().hashCode();
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }
}
