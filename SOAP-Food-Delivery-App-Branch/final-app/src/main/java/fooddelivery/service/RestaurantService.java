package fooddelivery.service;

import fooddelivery.model.Location;
import fooddelivery.model.MenuItem;
import fooddelivery.model.Restaurant;

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

    public Restaurant getById(String id) {
        for (Restaurant r : restaurants) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    public Restaurant findByName(String name) {
        for (Restaurant r : restaurants) {
            if (r.getName().equals(name)) return r;
        }
        return null;
    }

    // Admin remove restaurant

    public void removeRestaurant(String id) {
        restaurants.removeIf(r -> r.getId().equals(id));
    }

    // manage update hours

    public void updateOpenHours(String restaurantId, String hours) {
        Restaurant r = getById(restaurantId);
        if (r != null) r.setOpenHours(hours);
    }

    public void addMenuItemToRestaurant(String restaurantId, MenuItem item) {
        Restaurant r = getById(restaurantId);
        if (r != null) r.addMenuItem(item);
    }

    public void removeMenuItemFromRestaurant(String restaurantId, String itemId) {
        Restaurant r = getById(restaurantId);
        if (r != null) r.removeMenuItem(itemId);
    }

    // proximity
    private double calculateDistance(Location a, Location b) {
        double dx = a.getLatitude()  - b.getLatitude();
        double dy = a.getLongitude() - b.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Restaurant> findNearbyRestaurants(double latitude, double longitude) {
        Location userLoc = new Location();
        userLoc.setLatitude(latitude);
        userLoc.setLongitude(longitude);

        restaurants.sort((r1, r2) -> {
            double d1 = calculateDistance(userLoc, r1.getLocation());
            double d2 = calculateDistance(userLoc, r2.getLocation());
            return Double.compare(d1, d2);
        });

        return new ArrayList<>(restaurants);
    }
}