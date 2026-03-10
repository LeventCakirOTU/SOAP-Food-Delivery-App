package fooddelivery.services;

import fooddelivery.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {

    private List<Restaurant> restaurants;

    public RestaurantService() {
        restaurants = new ArrayList<>();
    }

    public void registerRestaurant(Restaurant r) {
        restaurants.add(r);
        System.out.println("Registered: " + r.getName());
    }

    // For simplicity, return all restaurants
    public List<Restaurant> findNearbyRestaurants(double latitude, double longitude) {
        return new ArrayList<>(restaurants);
    }
}