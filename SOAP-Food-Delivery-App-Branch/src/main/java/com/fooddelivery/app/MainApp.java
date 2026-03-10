package com.fooddelivery.app;

import com.fooddelivery.model.Location;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.service.RestaurantService;
import com.fooddelivery.service.UserService;
import com.fooddelivery.user.Customer;
import com.fooddelivery.user.Driver;
import com.fooddelivery.user.RestaurantOwner;
import com.fooddelivery.ui.MainFrame;
import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {

        RestaurantService restaurantService = new RestaurantService();
        UserService userService = new UserService();

        // Register sample restaurants
        Restaurant r1 = new Restaurant();
        r1.setId("1");
        r1.setName("Restaurant 1");
        r1.setCategory("General");
        r1.setAddress("123 Main St");
        r1.setOpenHours("9AM - 10PM");
        r1.setRating(4.5);
        Location loc1 = new Location();
        loc1.setLatitude(43.65);
        loc1.setLongitude(-79.38);
        r1.setLocation(loc1);

        Restaurant r2 = new Restaurant();
        r2.setId("2");
        r2.setName("Restaurant 2");
        r2.setCategory("General");
        r2.setAddress("456 King St");
        r2.setOpenHours("10AM - 11PM");
        r2.setRating(4.2);
        Location loc2 = new Location();
        loc2.setLatitude(43.66);
        loc2.setLongitude(-79.39);
        r2.setLocation(loc2);

        restaurantService.registerRestaurant(r1);
        restaurantService.registerRestaurant(r2);

        // Sample users
        Customer customer = new Customer();
        customer.setId("c1");
        customer.setName("Alice");
        customer.setEmail("alice@email.com");
        customer.setPassword("password!");
        customer.setAddress("789 Queen St");
        userService.registerUser(customer);

        Driver driver = new Driver();
        driver.setId("d1");
        driver.setName("Bob");
        driver.setEmail("bob@gmail.com");
        driver.setPassword("12345");
        driver.setVehicleType("Toyota Corolla 2020");
        userService.registerUser(driver);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setId("o1");
        owner.setName("Mike");
        owner.setEmail("mike@teamail.com");
        owner.setPassword("mikey77");
        userService.registerUser(owner);

        // Launch GUI
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new MainFrame(userService, restaurantService));
    }
}