package com.fabflix.Management;

import java.util.ArrayList;
import java.util.HashSet;

public class Star {
	private String id;
	private String name;
	private String dob;
	private String photo_url;
	private String bio;
	private String details;
	private String full_dob;
	private String dod;

	private HashSet<Movie> starredin;
	
	public Star(){
		starredin = new HashSet<Movie>();
	}
	
	/** Set Methods */
	public void setId(String id){
		this.id = id;
	}
	
	public void set_name(String name){
		this.name = name;
	}
	
	public void setdob(String dob) {
		this.dob = dob;
	}
	
	public void setPhoto(String photo_url) {
		
		this.photo_url = photo_url;
	}

	public void setBio(String bio) {

		this.bio = bio;
	}

	public void setdod(String dod) {
		this.dod = dod;
	}

	public void setDetails(String details) {

		this.details = details;
	}

	public void addMovie(Movie movie) {
		starredin.add(movie);
	}
	
	/** Accessor Methods */
	public String getid() {
		return id;
	}

	public String getName()
	{
		return name;
	}
	
	public String getdob() {
		
		return dob;
	}
	
	public String getPhoto() {
		
		return photo_url;
	}

	public String getBio() {

		if(bio == null || bio.length() == 0)
		{
			return "No Biography  Available";
		}

		return bio;
	}

	public String getDod() {

		return dod;
	}

	public String getDetails() {
		if(details == null || details.length() == 0)
		{
			return "No Birth Details Available";
		}

		return "Born " + details;
	}

	public ArrayList<Movie> getStarredin() {
		
		return new ArrayList<Movie>(starredin);
	}
	
	/** To String Method */
	public String toString() {

		return name;
		
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getid().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }

}
