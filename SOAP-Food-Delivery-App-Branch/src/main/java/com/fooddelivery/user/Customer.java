package com.fooddelivery.user;

import com.fooddelivery.model.Order;
import com.fooddelivery.user.User;
import java.util.List;

public class Customer extends User {

    private String      address;
    private List<Order> orderHistory;

    public Customer() {}

    public String getAddress()              { return address; }
    public void   setAddress(String address){ this.address = address; }

    public List<Order> getOrderHistory()                    { return orderHistory; }
    public void        setOrderHistory(List<Order> history) { this.orderHistory = history; }

    public void browseRestaurants()             {}
    public void placeOrder(Order order)         {}
    public void viewOrderStatus(String orderId) {}
}
