package fooddelivery.app;

import fooddelivery.model.Location;
import fooddelivery.model.Restaurant;
import fooddelivery.service.RestaurantService;
import fooddelivery.service.UserService;
import fooddelivery.user.Customer;
import fooddelivery.user.Driver;
import fooddelivery.user.RestaurantOwner;
import fooddelivery.ui.MainFrame;
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

        // Menu items for Restaurant 1
        fooddelivery.model.MenuItem r1i1 = new fooddelivery.model.MenuItem();
        r1i1.setId("m1");
        r1i1.setName("Chicken");
        r1i1.setDescription("Grilled chicken");
        r1i1.setPrice(10.0);
        r1i1.setCategory("MEAT");
        r1.addMenuItem(r1i1);

        fooddelivery.model.MenuItem r1i2 = new fooddelivery.model.MenuItem();
        r1i2.setId("m2");
        r1i2.setName("Fries");
        r1i2.setDescription("Crispy fries");
        r1i2.setPrice(4.0);
        r1i2.setCategory("SIDE");
        r1.addMenuItem(r1i2);

        // Menu items for Restaurant 2
        fooddelivery.model.MenuItem r2i1 = new fooddelivery.model.MenuItem();
        r2i1.setId("m3");
        r2i1.setName("Salad");
        r2i1.setDescription("Fresh salad");
        r2i1.setPrice(6.01);
        r2i1.setCategory("VEGETARIAN");
        r2.addMenuItem(r2i1);

        fooddelivery.model.MenuItem r2i2 = new fooddelivery.model.MenuItem();
        r2i2.setId("m4");
        r2i2.setName("Burger");
        r2i2.setDescription("Beef burger");
        r2i2.setPrice(9.3);
        r2i2.setCategory("MEAT");
        r2.addMenuItem(r2i2);

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