package com.fooddelivery.user;

import com.fooddelivery.model.Restaurant;
import com.fooddelivery.user.User;

public class RestaurantOwner extends User {

    private Restaurant restaurant;

    public RestaurantOwner() {}

    public Restaurant getRestaurant()               { return restaurant; }
    public void       setRestaurant(Restaurant r)   { this.restaurant = r; }

    public void updateMenu()        {}
    public void viewOrders()        {}
    public void manageProfile()     {}
}
