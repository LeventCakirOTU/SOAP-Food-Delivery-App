package fooddelivery.app;

import fooddelivery.model.*;
import fooddelivery.services.RestaurantService;
import fooddelivery.services.UserService;

import fooddelivery.users.Customer;
import fooddelivery.users.Driver;
import fooddelivery.users.RestaurantOwner;
import fooddelivery.users.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("App Starting");

        RestaurantService restaurantService = new RestaurantService();
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        Restaurant r1 = new Restaurant();
        r1.setId("1");
        r1.setName("Restaurant 1");
        Location loc1 = new Location();
        loc1.setLatitude(43.65);
        loc1.setLongitude(-79.38);
        r1.setLocation(loc1);

        Restaurant r2 = new Restaurant();
        r2.setId("2");
        r2.setName("Restaurant 2");
        Location loc2 = new Location();
        loc2.setLatitude(43.66);
        loc2.setLongitude(-79.39);
        r2.setLocation(loc2);

        MenuItem r1i1 = new MenuItem();
        r1i1.setId("m1");
        r1i1.setName("Chicken");
        r1i1.setDescription("Grilled chicken");
        r1i1.setPrice(10.0);
        r1i1.setCategory("MEAT");
        r1.addMenuItem(r1i1);

        MenuItem r1i2 = new MenuItem();
        r1i2.setId("m2");
        r1i2.setName("Fries");
        r1i2.setDescription("Crispy fries");
        r1i2.setPrice(4);
        r1i2.setCategory("SIDE");
        r1.addMenuItem(r1i2);

        MenuItem r2i1 = new MenuItem();
        r2i1.setId("m3");
        r2i1.setName("Salad");
        r2i1.setDescription("Fresh salad");
        r2i1.setPrice(6.01);
        r2i1.setCategory("VEGETARIAN");
        r2.addMenuItem(r2i1);

        MenuItem r2i2 = new MenuItem();
        r2i2.setId("m4");
        r2i2.setName("Burger");
        r2i2.setDescription("Beef burger");
        r2i2.setPrice(9.3);
        r2i2.setCategory("meat");
        r2.addMenuItem(r2i2);

        restaurantService.registerRestaurant(r1);
        restaurantService.registerRestaurant(r2);

        Customer customer = new Customer();
        customer.setId("c1");
        customer.setName("Alice");
        customer.setEmail("alice@email.com");
        customer.setPassword("password!");
        userService.registerUser(customer);

        Driver driver = new Driver();
        driver.setId("d1");
        driver.setName("Bob");
        driver.setEmail("bob@gmail.com");
        driver.setPassword("12345");
        userService.registerUser(driver);

        RestaurantOwner owner = new RestaurantOwner();
        owner.setId("o1");
        owner.setName("Mike");
        owner.setEmail("mike@teamail.com");
        owner.setPassword("mikey77");
        userService.registerUser(owner);

        while (true) {

            User loggedInUser = null;

            while (loggedInUser == null) {

                System.out.println("\n=== Welcome ===");
                System.out.println("1. Login");
                System.out.println("2. Sign Up");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                if (choice.equals("2")) {
                    System.out.println("\n=== Sign Up ===");
                    System.out.println("1. Customer\n2. Driver\n3. Restaurant Owner");
                    String type = scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    User newUser = null;
                    if (type.equals("1")) newUser = new Customer();
                    else if (type.equals("2")) newUser = new Driver();
                    else if (type.equals("3")) newUser = new RestaurantOwner();

                    if (newUser != null) {
                        newUser.setId("u" + System.currentTimeMillis());
                        newUser.setName(name);
                        newUser.setEmail(email);
                        newUser.setPassword(password);
                        userService.registerUser(newUser);
                    }

                } else if (choice.equals("1")) {

                    while (loggedInUser == null) {
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        loggedInUser = userService.login(email, password);

                        if (loggedInUser == null) {
                            System.out.println("Login failed.");
                        }
                    }
                }
            }

            System.out.println("Welcome " + loggedInUser.getName());

            if (loggedInUser instanceof RestaurantOwner) {

                RestaurantOwner restaurantOwner = (RestaurantOwner) loggedInUser;
                boolean done = false;

                while (!done) {
                    System.out.println("\n=== Owner Menu ===");
                    System.out.println("1. Add Restaurant");
                    System.out.println("2. View My Restaurants");
                    System.out.println("3. Add Menu Item");
                    System.out.println("4. Logout");

                    String option = scanner.nextLine();

                    switch (option) {

                        case "1":
                            Restaurant r = new Restaurant();
                            r.setId("r" + System.currentTimeMillis());

                            System.out.print("Restaurant name: ");
                            r.setName(scanner.nextLine());

                            Location loc = new Location();
                            System.out.print("Latitude: ");
                            loc.setLatitude(Double.parseDouble(scanner.nextLine()));
                            System.out.print("Longitude: ");
                            loc.setLongitude(Double.parseDouble(scanner.nextLine()));

                            r.setLocation(loc);
                            restaurantOwner.addRestaurant(r, restaurantService);
                            break;

                        case "2":
                            for (Restaurant res : restaurantOwner.getRestaurants()) {
                                System.out.println(res.getName());
                            }
                            break;

                        case "3":
                            List<Restaurant> resList = restaurantOwner.getRestaurants();
                            if (resList.isEmpty()) {
                                System.out.println("No restaurants.");
                                break;
                            }

                            int index = -1;
                            while (true) {
                                System.out.println("Select a restaurant:");
                                for (int i = 0; i < resList.size(); i++) {
                                    System.out.println(i + 1 + ": " + resList.get(i).getName());
                                }
                                System.out.print("Enter number: ");
                                try {
                                    index = Integer.parseInt(scanner.nextLine()) - 1;
                                    if (index >= 0 && index < resList.size()) break;
                                } catch (Exception e) {}
                                System.out.println("Invalid selection.");
                            }

                            Restaurant selected = resList.get(index);

                            MenuItem item = new MenuItem();
                            item.setId("m" + System.currentTimeMillis());

                            System.out.print("Item name: ");
                            item.setName(scanner.nextLine());

                            System.out.print("Description: ");
                            item.setDescription(scanner.nextLine());

                            System.out.print("Price: ");
                            item.setPrice(Double.parseDouble(scanner.nextLine()));

                            System.out.print("Category: ");
                            item.setCategory(scanner.nextLine().toUpperCase());

                            selected.addMenuItem(item);
                            break;

                        case "4":
                            done = true;
                            System.out.println("Logging out...");
                            break;
                    }
                }
            }

            if (loggedInUser instanceof Customer) {

                Customer c = (Customer) loggedInUser;
                boolean done = false;

                while (!done) {

                    System.out.println("\n=== Customer Menu ===");
                    System.out.println("1. Browse Restaurants");
                    System.out.println("2. View Cart");
                    System.out.println("3. Checkout");
                    System.out.println("4. Logout");

                    String option = scanner.nextLine();

                    switch (option) {

                        case "1":
                            List<Restaurant> restaurants = restaurantService.findNearbyRestaurants(0, 0);

                            int rIndex = -1;
                            while (true) {
                                for (int i = 0; i < restaurants.size(); i++) {
                                    System.out.println(i + 1 + ": " + restaurants.get(i).getName());
                                }
                                try {
                                    rIndex = Integer.parseInt(scanner.nextLine()) - 1;
                                    if (rIndex >= 0 && rIndex < restaurants.size()) break;
                                } catch (Exception e) {}
                                System.out.println("Invalid selection.");
                            }

                            Restaurant chosen = restaurants.get(rIndex);

                            System.out.print("Filter category (or press enter to skip): ");
                            String filter = scanner.nextLine().toUpperCase();

                            List<MenuItem> items;
                            if (filter.isEmpty()) {
                                items = chosen.getMenu().getItems();
                            } else {
                                items = chosen.getMenu().getItemsByCategory(filter);
                            }

                            if (items.isEmpty()) {
                                System.out.println("No items found.");
                                break;
                            }

                            int itemIndex = -1;
                            while (true) {
                                for (int i = 0; i < items.size(); i++) {
                                    MenuItem mi = items.get(i);
                                    System.out.println(i + 1 + ": " + mi.getName() + " - $" + mi.getPrice());
                                }
                                System.out.print("Select item #: ");
                                try {
                                    itemIndex = Integer.parseInt(scanner.nextLine()) - 1;
                                    if (itemIndex >= 0 && itemIndex < items.size()) break;
                                } catch (Exception e) {}
                                System.out.println("Invalid selection.");
                            }

                            System.out.print("Quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine());

                            c.addToCart(items.get(itemIndex), qty);
                            break;

                        case "2":
                            Order cart = c.getCurrentOrder();

                            for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {
                                System.out.println(entry.getKey().getName() + " x" + entry.getValue());
                            }
                            break;

                        case "3":
                            Order order = c.getCurrentOrder();
                            order.setOrderId("o" + System.currentTimeMillis());
                            order.setCustomerId(c.getId());
                            order.setStatus("PLACED");

                            c.placeOrder(order);
                            break;

                        case "4":
                            done = true;
                            System.out.println("Logging out...");
                            break;
                    }
                }
            }
        }
    }
}