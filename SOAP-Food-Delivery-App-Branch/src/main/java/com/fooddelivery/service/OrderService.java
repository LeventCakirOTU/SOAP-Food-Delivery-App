package com.fooddelivery.service;

import com.fooddelivery.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public void placeOrder(Order order)         {}
    public void cancelOrder(String orderId)     {}
    public Order getOrder(String orderId)       { return null; }
    public List<Order> getOrdersByCustomer(String customerId) { return new ArrayList<>(); }
}
