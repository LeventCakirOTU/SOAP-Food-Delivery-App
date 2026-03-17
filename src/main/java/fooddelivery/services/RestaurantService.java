package com.fooddelivery.service;

import com.fooddelivery.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {

    private final List<Restaurant> restaurants = new ArrayList<>();

    public void registerRestaurant(Restaurant r) {
        restaurants.add(r);
        System.out.println("Registered: " + r.getName());
    }

    public List<Restaurant> getAll() {
        return new ArrayList<>(restaurants);
    }

    public List<Restaurant> findNearbyRestaurants(double latitude, double longitude) {
        return new ArrayList<>(restaurants);
    }
}
