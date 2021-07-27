package com.fabflix.Management;

import java.util.HashMap;
import java.util.Map;

public class User {
	private String id;

	private String first_name;
	private String last_name;
	private String email;
	private String password;

	private String address;
	private String unit;
	private String city;
	private String state;
	private String postcode;

	private String cc_id;
	private String cc_fname;
	private String cc_lname;
	private String cc_date;

	private Cart cart;
	private Map<String,Order> orders;
	
	public User() {
		this.cart = new Cart();
		this.orders = new HashMap<String,Order>();
	}
	/**Set Methods */
	
	public void setid(String id){
		this.id = id;
	}

	public void setfirst_name(String first_name){
		this.first_name = first_name;
	}
	
	public void setlast_name(String last_name){
		this.last_name = last_name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public void setPassword(String password){
		this.password = password;
	}


	public void setAddress(String address){
		this.address = address;
	}
	
	public void setUnit(String unit){
		this.unit = unit;
	}

	public void setCity(String city){
		this.city = city;
	}

	public void setState(String state){
		this.state = state;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}


	public void setcc_id(String cc_id){
		this.cc_id = cc_id;
	}

	public void setcc_fname(String fname){
		this.cc_fname = fname;
	}

	public void setcc_lname(String lname){
		this.cc_lname = lname;
	}

	public void setcc_expiration(String date){
		this.cc_date = date;
	}


	public void setCart(Cart cart){
		this.cart = cart;
	}

	/**Get Methods */
	public String getid(){
		return id;
	}
	
	public String getfirst_name(){
		return first_name;
	}
	
	public String getlast_name(){
		return last_name;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	
	public String getAddress(){
		return address;
	}

	public String getUnit(){
		return unit;
	}

	public String getCity(){
		return city;
	}

	public String getState(){
		return state;
	}

	public String getPostcode(){
		return  postcode;
	}


	public String getcc_id(){
		return cc_id;
	}

	public String getcc_fname(){
		return cc_fname;
	}

	public String getcc_lname(){
		return cc_lname;
	}

	public String getcc_expiration(){
		return cc_date;
	}

	
	public Cart getCart(){
		return cart;
	}

	public Map<String,Order> getOrders() { return  orders;}
	
	/** Add methods */
	public void addMovieToCart(Movie movie) {
		cart.addItem(movie);
	}

	public void addOrder(Order order) {
		orders.put(order.getOrderId(),order);
	}

}
