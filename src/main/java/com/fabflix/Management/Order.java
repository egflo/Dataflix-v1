package com.fabflix.Management;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class Order {
    String order_id;
    Map<Movie,Integer> items;
    String order_date;

    public Order() {
        //Comparision for HashMap Movies is done with movie id
        this.items = new HashMap<Movie,Integer>();
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public void setSaleDate(String date) {
        this.order_date = date;
    }

    public void addItem(Movie movie, Integer qty){
        items.put(movie,qty);
    }

    public String getOrderId()  {
        return order_id;
    }

    public String getSaleDate()  {
        return order_date;
    }

    public double getTotal() {
        double total = 0;

        for (Map.Entry<Movie,Integer> entry : items.entrySet()) {
            Movie movie = entry.getKey();
            Integer qty = entry.getValue();

            total += movie.getPrice() * qty;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    public Map<Movie,Integer> getMovies() { return items;}

    public Map<Movie,Integer> getItems() {
        return items;
    }

    public void emptyOrders() {
        items.clear();
    }


}
