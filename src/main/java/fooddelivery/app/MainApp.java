package fooddelivery.app;

import fooddelivery.model.Location;
import fooddelivery.model.Restaurant;
import fooddelivery.services.RestaurantService;
import fooddelivery.services.UserService;

import fooddelivery.users.Customer;
import fooddelivery.users.Driver;
import fooddelivery.users.RestaurantOwner;
import fooddelivery.users.User;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("App Starting");

        RestaurantService restaurantService = new RestaurantService();
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        // Register sample restaurants
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

        restaurantService.registerRestaurant(r1);
        restaurantService.registerRestaurant(r2);

        // Display restaurants
        List<Restaurant> restaurants = restaurantService.findNearbyRestaurants(0, 0);
        for (Restaurant r : restaurants) {
            System.out.println(r.getName() + " - " +
                    r.getLocation().getLatitude() + ", " +
                    r.getLocation().getLongitude());
        }

        // Optional: sample users
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

        User loggedInUser = null;

        while (loggedInUser == null) {

            System.out.println("\n=== Welcome ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("2")) {
                System.out.println("\n=== Sign Up ===");
                System.out.println("Account type:");
                System.out.println("1. Customer");
                System.out.println("2. Driver");
                System.out.println("3. Restaurant Owner");
                System.out.print("Choose account type: ");
                String type = scanner.nextLine();

                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User newUser = null;
                if (type.equals("1")) {
                    newUser = new Customer();
                } else if (type.equals("2")) {
                    newUser = new Driver();
                } else if (type.equals("3")) {
                    newUser = new RestaurantOwner();
                } else {
                    System.out.println("Invalid account type.");
                    continue;
                }

                newUser.setId("u" + System.currentTimeMillis());
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);

                userService.registerUser(newUser);

            } else if (choice.equals("1")) {

                while (loggedInUser == null) {
                    System.out.println("\n=== Login ===");
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    loggedInUser = userService.login(email, password);

                    if (loggedInUser == null) {
                        System.out.println("Login failed. Incorrect email or password. Please try again.");
                    }
                }

            } else {
                System.out.println("Invalid option.");
            }
        }

        System.out.println("Welcome " + loggedInUser.getName());
        scanner.close();
    }
}