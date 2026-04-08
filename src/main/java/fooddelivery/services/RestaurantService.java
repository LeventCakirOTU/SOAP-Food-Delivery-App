package fooddelivery.service;

import fooddelivery.model.Location;
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
