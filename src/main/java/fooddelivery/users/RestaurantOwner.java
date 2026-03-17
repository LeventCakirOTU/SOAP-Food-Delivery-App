package fooddelivery.users;

import fooddelivery.model.Restaurant;
import fooddelivery.services.RestaurantService;

import java.util.ArrayList;
import java.util.List;

public class RestaurantOwner extends User {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantOwner() {}

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    // Add restaurant to both owner and system
    public void addRestaurant(Restaurant r, RestaurantService service) {
        restaurants.add(r);
        service.registerRestaurant(r);
        System.out.println("Restaurant added: " + r.getName());
    }

    public void updateMenu() {}
    public void viewOrders() {}
    public void manageProfile() {}
}