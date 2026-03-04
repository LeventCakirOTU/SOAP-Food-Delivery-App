package fooddelivery.users;

import fooddelivery.model.Restaurant;

public class RestaurantOwner extends User {

    private Restaurant restaurant;

    public RestaurantOwner() {}

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public void updateMenu() {}
    public void viewOrders() {}
    public void manageProfile() {}
}