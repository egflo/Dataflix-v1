package com.fabflix.Management;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	HashMap<Movie,Integer> items;

	public Cart() {
		super();
		//Comparision for HashMap Movies is done with movie id
		this.items = new HashMap<Movie,Integer>();
	}
	
	public boolean addItem(Movie movie){
		//Add Single Item (Movie)

		if(!items.containsKey(movie)) {
			items.put(movie, 1);
		}
		
		else {
			if(items.get(movie) >= MyConstants.MAX_QTY) {
				return false;
			}

			Integer quantity = items.get(movie);
			items.put(movie, quantity + 1);
		}

		return true;
	}
	
	public boolean setQuantity(Movie movie, Integer quantity){

		if(items.get(movie) >= MyConstants.MAX_QTY) {
			return false;
		}

		if(quantity >= MyConstants.MAX_QTY) {
			return false;
		}


		if(items.containsKey(movie))
		{
			if(quantity == 0)
			{
				items.remove(movie);
			}
			else
			{
				items.put(movie,quantity);
			}
		}

		return true;
	}

	public void setItems(HashMap<Movie,Integer> items) {
		this.items = items;
	}

	public void setItemsJSON(String items) {
		Gson gson = new Gson();
		Map<String,String> object = gson.fromJson(items,Map.class);

		this.items = new HashMap<Movie,Integer>();
		for(Map.Entry<String,String> entry: object.entrySet()){

		}
	}
	
	public HashMap<Movie,Integer> getItems()
	{
		return items;
	}
	
	public void emptyCart()
	{
		items.clear();
	}

	public void removeItem(Movie movie) {
		items.remove(movie);
	}
	
	public double calculate_total() {
		double total = 0;

		for (Map.Entry<Movie, Integer> entry : items.entrySet()) {
		    Movie key = entry.getKey();
		    Integer value = entry.getValue();
		    
		    total += key.getPrice() * value;
		}
		
		return Math.round(total * 100.0) / 100.0;
	}

}
