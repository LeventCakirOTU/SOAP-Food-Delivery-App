package fooddelivery.app;

import fooddelivery.model.*;
import fooddelivery.services.DeliveryService;
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
        DeliveryService deliveryService = new DeliveryService();
        Scanner scanner = new Scanner(System.in);

        // ===== SAMPLE RESTAURANTS =====
        Restaurant r1 = new Restaurant();
        r1.setId("1");
        r1.setName("Restaurant 1");

        Location loc1 = new Location();
        loc1.setLatitude(20);
        loc1.setLongitude(-100);
        r1.setLocation(loc1);

        r1.setOpenTime("07:00");
        r1.setCloseTime("21:00");

        Restaurant r2 = new Restaurant();
        r2.setId("2");
        r2.setName("Restaurant 2");

        Location loc2 = new Location();
        loc2.setLatitude(40);
        loc2.setLongitude(-110);
        r2.setLocation(loc2);

        r2.setOpenTime("09:00");
        r2.setCloseTime("22:00");

        MenuItem r1i1 = new MenuItem();
        r1i1.setId("m1");
        r1i1.setName("Chicken");
        r1i1.setDescription("Grilled chicken");
        r1i1.setPrice(10.0);
        r1i1.setCategory("MEAT");
        r1i1.setPreparationTime(15);
        r1.addMenuItem(r1i1);

        MenuItem r1i2 = new MenuItem();
        r1i2.setId("m2");
        r1i2.setName("Fries");
        r1i2.setDescription("Crispy fries");
        r1i2.setPrice(4);
        r1i2.setCategory("SIDE");
        r1i2.setPreparationTime(5);
        r1.addMenuItem(r1i2);

        MenuItem r2i1 = new MenuItem();
        r2i1.setId("m3");
        r2i1.setName("Salad");
        r2i1.setDescription("Fresh salad");
        r2i1.setPrice(6.01);
        r2i1.setCategory("VEGETARIAN");
        r2i1.setPreparationTime(7);
        r2.addMenuItem(r2i1);

        MenuItem r2i2 = new MenuItem();
        r2i2.setId("m4");
        r2i2.setName("Burger");
        r2i2.setDescription("Beef burger");
        r2i2.setPrice(9.3);
        r2i2.setCategory("MEAT");
        r2i2.setPreparationTime(12);
        r2.addMenuItem(r2i2);

        restaurantService.registerRestaurant(r1);
        restaurantService.registerRestaurant(r2);

        // ===== SAMPLE USERS =====
        Customer customer = new Customer();
        customer.setId("c1");
        customer.setName("Alice");
        customer.setEmail("alice@email.com");
        customer.setPassword("password!");

        Location cLoc = new Location();
        cLoc.setLatitude(10.2);
        cLoc.setLongitude(-10.4);
        customer.setLocation(cLoc);

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

        // ===== SAMPLE DELIVERY TASK =====
        DeliveryTask sampleTask = new DeliveryTask();
        sampleTask.setTaskId("t1");
        sampleTask.setOrderId("sampleOrder");
        sampleTask.setPickupLocation("Restaurant 1");
        sampleTask.setDropoffLocation("123 Main St");
        sampleTask.setInstructions("Leave at door");
        sampleTask.setStatus("PENDING");
        deliveryService.addTask(sampleTask);

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

                        // ✅ ADD LOCATION FOR CUSTOMER
                        if (newUser instanceof Customer) {
                            Location userLoc = new Location();
                            System.out.print("Your latitude: ");
                            userLoc.setLatitude(Double.parseDouble(scanner.nextLine()));
                            System.out.print("Your longitude: ");
                            userLoc.setLongitude(Double.parseDouble(scanner.nextLine()));
                            ((Customer) newUser).setLocation(userLoc);
                        }

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

            // ===== CUSTOMER MENU =====
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

                            List<Restaurant> restaurants =
                                    restaurantService.findNearbyRestaurants(
                                            c.getLocation().getLatitude(),
                                            c.getLocation().getLongitude()
                                    );

                            System.out.println("\nRestaurants:");
                            for (int i = 0; i < restaurants.size(); i++) {

                                Restaurant r = restaurants.get(i);

                                double distance = Math.sqrt(
                                        Math.pow(c.getLocation().getLatitude() - r.getLocation().getLatitude(), 2) +
                                                Math.pow(c.getLocation().getLongitude() - r.getLocation().getLongitude(), 2)
                                );

                                System.out.println((i + 1) + ". " + r.getName()
                                        + " | Rating: " + String.format("%.1f", r.getAverageRating())
                                        + " | Distance: " + String.format("%.2f", distance)
                                        + " | Hours: " + r.getOpenTime() + "-" + r.getCloseTime());
                            }

                            System.out.println((restaurants.size() + 1) + ". Back");
                            System.out.print("Choose: ");

                            int rChoice = Integer.parseInt(scanner.nextLine());
                            if (rChoice == restaurants.size() + 1) break;

                            Restaurant chosen = restaurants.get(rChoice - 1);

                            // ✅ FILTER
                            System.out.print("Filter by category (or press Enter): ");
                            String filter = scanner.nextLine();

                            List<MenuItem> items;
                            if (!filter.isEmpty()) {
                                items = chosen.getMenu().getItemsByCategory(filter);
                            } else {
                                items = chosen.getMenu().getItems();
                            }

                            System.out.println("\nMenu:");
                            for (int i = 0; i < items.size(); i++) {
                                MenuItem m = items.get(i);
                                if (m.isAvailable()) {
                                    System.out.println((i + 1) + ". " + m.getName() + " $" + m.getPrice() + " (" + m.getPreparationTime() + " minute prep time)");
                                }
                            }

                            System.out.println((items.size() + 1) + ". Back");
                            int itemIndex = Integer.parseInt(scanner.nextLine());

                            if (itemIndex == items.size() + 1) break;

                            System.out.print("Quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine());

                            c.addToCart(items.get(itemIndex - 1), qty);

                            // ⭐ RATE
                            System.out.print("Rate restaurant (1-5): ");
                            int rating = Integer.parseInt(scanner.nextLine());
                            chosen.addRating(rating);

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

                            System.out.print("Instructions: ");
                            order.setInstructions(scanner.nextLine());

                            c.placeOrder(order);

                            DeliveryTask task = new DeliveryTask();
                            task.setTaskId("t" + System.currentTimeMillis());
                            task.setOrderId(order.getOrderId());
                            task.setPickupLocation("Restaurant");
                            task.setDropoffLocation("Customer");
                            task.setInstructions(order.getInstructions());
                            task.setStatus("PENDING");

                            deliveryService.addTask(task);

                            System.out.println("Order placed!");
                            break;

                        case "4":
                            done = true;
                            break;
                    }
                }
            }

            // ===== DRIVER MENU =====
            if (loggedInUser instanceof Driver) {

                Driver d = (Driver) loggedInUser;
                boolean done = false;

                while (!done) {
                    System.out.println("\n=== Driver Menu ===");
                    System.out.println("1. View Available Tasks");
                    System.out.println("2. My Tasks");
                    System.out.println("3. Logout");

                    String option = scanner.nextLine();

                    switch (option) {

                        case "1":

                            while (true) {
                                List<DeliveryTask> allTasks = deliveryService.getAllTasks();

                                List<DeliveryTask> pending = new java.util.ArrayList<>();
                                for (DeliveryTask t : allTasks) {
                                    if ("PENDING".equals(t.getStatus())) {
                                        pending.add(t);
                                    }
                                }

                                if (pending.isEmpty()) {
                                    System.out.println("No available tasks.");
                                    break;
                                }

                                System.out.println("\nAvailable Tasks:");

                                for (int i = 0; i < pending.size(); i++) {
                                    DeliveryTask t = pending.get(i);
                                    System.out.println((i + 1) + ". Order " + t.getOrderId()
                                            + " | " + t.getPickupLocation()
                                            + " -> " + t.getDropoffLocation());
                                }

                                System.out.println((pending.size() + 1) + ". Back");
                                System.out.print("Choose: ");

                                int choice;
                                try {
                                    choice = Integer.parseInt(scanner.nextLine());
                                } catch (Exception e) {
                                    System.out.println("Invalid input.");
                                    continue;
                                }

                                if (choice == pending.size() + 1) break;

                                if (choice < 1 || choice > pending.size()) {
                                    System.out.println("Invalid choice.");
                                    continue;
                                }

                                DeliveryTask selected = pending.get(choice - 1);

                                while (true) {
                                    System.out.println("\n1. Accept");
                                    System.out.println("2. Reject");
                                    System.out.println("3. Back");
                                    System.out.print("Choose: ");

                                    String action = scanner.nextLine();

                                    if (action.equals("1")) {
                                        d.acceptTask(selected);
                                        break;
                                    } else if (action.equals("2")) {
                                        d.rejectTask(selected);
                                        break;
                                    } else if (action.equals("3")) {
                                        break;
                                    } else {
                                        System.out.println("Invalid option.");
                                    }
                                }
                            }
                            break;

                        case "2":
                            List<DeliveryTask> myTasks = d.getTasks();

                            if (myTasks.isEmpty()) {
                                System.out.println("No accepted tasks.");
                            } else {
                                System.out.println("\nMy Tasks:");
                                for (int i = 0; i < myTasks.size(); i++) {
                                    DeliveryTask t = myTasks.get(i);
                                    System.out.println((i + 1) + ". Task " + t.getTaskId());
                                    System.out.println("   Order: " + t.getOrderId());
                                    System.out.println("   Instructions: " + t.getInstructions());
                                    System.out.println("   Status: " + t.getStatus());
                                }
                            }
                            break;

                        case "3":
                            done = true;
                            break;
                    }
                }
            }
// ===== OWNER MENU =====
            if (loggedInUser instanceof RestaurantOwner) {

                RestaurantOwner restaurantOwner = (RestaurantOwner) loggedInUser;
                boolean done = false;

                while (!done) {
                    System.out.println("\n=== Owner Menu ===");
                    System.out.println("1. Add Restaurant");
                    System.out.println("2. View My Restaurants");
                    System.out.println("3. Add Menu Item");
                    System.out.println("4. Toggle Item Availability");
                    System.out.println("5. Logout");

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
                            System.out.print("Open time (e.g. 07:00): ");
                            r.setOpenTime(scanner.nextLine());

                            System.out.print("Close time (e.g. 21:00): ");
                            r.setCloseTime(scanner.nextLine());
                            restaurantOwner.addRestaurant(r, restaurantService);
                            break;

                        case "2":
                            List<Restaurant> myRestaurants = restaurantOwner.getRestaurants();
                            if (myRestaurants.isEmpty()) {
                                System.out.println("No restaurants found.");
                                break;
                            }

                            System.out.println("\nMy Restaurants:");
                            for (int i = 0; i < myRestaurants.size(); i++) {
                                System.out.println((i + 1) + ". " + myRestaurants.get(i).getName());
                            }
                            System.out.println((myRestaurants.size() + 1) + ". Back");
                            System.out.print("Choose: ");
                            int rChoice;
                            try {
                                rChoice = Integer.parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                break;
                            }
                            if (rChoice == myRestaurants.size() + 1) break;
                            if (rChoice < 1 || rChoice > myRestaurants.size()) {
                                System.out.println("Invalid choice.");
                            }
                            break;

                        case "3":
                            List<Restaurant> resList = restaurantOwner.getRestaurants();
                            if (resList.isEmpty()) {
                                System.out.println("No restaurants available.");
                                break;
                            }

                            // Let owner choose which restaurant to add item to
                            System.out.println("\nSelect a restaurant to add menu item:");
                            for (int i = 0; i < resList.size(); i++) {
                                System.out.println((i + 1) + ". " + resList.get(i).getName());
                            }
                            System.out.println((resList.size() + 1) + ". Back");
                            System.out.print("Choose: ");

                            int selIndex;
                            try {
                                selIndex = Integer.parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                break;
                            }

                            if (selIndex == resList.size() + 1) break;
                            if (selIndex < 1 || selIndex > resList.size()) {
                                System.out.println("Invalid choice.");
                                break;
                            }

                            Restaurant selected = resList.get(selIndex - 1);

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

                            System.out.print("Preparation time (minutes): ");
                            item.setPreparationTime(Integer.parseInt(scanner.nextLine()));

                            selected.addMenuItem(item);
                            System.out.println("Menu item added to " + selected.getName());
                            break;

                        case "4":
                            List<Restaurant> list = restaurantOwner.getRestaurants();
                            if (list.isEmpty()) break;

                            System.out.println("\nSelect restaurant to toggle items:");
                            for (int i = 0; i < list.size(); i++) {
                                System.out.println((i + 1) + ". " + list.get(i).getName());
                            }
                            System.out.println((list.size() + 1) + ". Back");
                            System.out.print("Choose: ");

                            int restIndex;
                            try {
                                restIndex = Integer.parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                break;
                            }
                            if (restIndex == list.size() + 1) break;
                            if (restIndex < 1 || restIndex > list.size()) {
                                System.out.println("Invalid choice.");
                                break;
                            }

                            Restaurant res = list.get(restIndex - 1);
                            List<MenuItem> items = res.getMenu().getItems();

                            if (items.isEmpty()) {
                                System.out.println("No menu items.");
                                break;
                            }

                            System.out.println("\nMenu Items:");
                            for (int i = 0; i < items.size(); i++) {
                                MenuItem mi = items.get(i);
                                System.out.println((i + 1) + ". " + mi.getName() + " (" + (mi.isAvailable() ? "Available" : "Unavailable") + ")");
                            }
                            System.out.println((items.size() + 1) + ". Back");
                            System.out.print("Choose item: ");

                            int itemChoice;
                            try {
                                itemChoice = Integer.parseInt(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("Invalid input.");
                                break;
                            }

                            if (itemChoice == items.size() + 1) break;
                            if (itemChoice < 1 || itemChoice > items.size()) {
                                System.out.println("Invalid choice.");
                                break;
                            }

                            MenuItem mi = items.get(itemChoice - 1);
                            mi.setAvailable(!mi.isAvailable());
                            System.out.println(mi.getName() + " availability toggled to " + (mi.isAvailable() ? "Available" : "Unavailable"));
                            break;

                        case "5":
                            done = true;
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                }
            }
        }
    }
}