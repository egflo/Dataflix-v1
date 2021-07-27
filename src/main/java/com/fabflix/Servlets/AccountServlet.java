package com.fabflix.Servlets;

import com.fabflix.Management.Cart;
import com.fabflix.Management.Order;
import com.fabflix.Management.User;
import com.fabflix.Search.Search;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AccountServlet", value = "/AccountServlet")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            //Return an existing Session or Create a New one
            HttpSession session = request.getSession();

            //Get user from session
            User session_user = (User)session.getAttribute("User");

            Search search = new Search();

            Connection connection = search.getConnection();
            User user = search.searchAccount(session_user.getid(), connection);
            Map<String, Order> orders = user.getOrders();

            //Send it back to JSP to show to user
            request.setAttribute("user", user);
            request.setAttribute("orders", orders.values());
            RequestDispatcher view = request.getRequestDispatcher("account.jsp");
            view.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String,String[]> data = (Map<String,String[]>) request.getParameterMap();

        Map<String,String> customer_update = new HashMap<String,String>();
        for (Map.Entry<String,String[]> entry : data.entrySet()){
            String key = entry.getKey();
            String[] value = entry.getValue();

             if(value.length == 0) {
                customer_update.put(key,"");
             }
             else {
                customer_update.put(key,value[0]);
            }
         }

        for(Map.Entry<String, String> a: customer_update.entrySet()){
                System.out.println(a.getKey() + " " + a.getValue());
        }

        doGet(request, response);
    }
}
