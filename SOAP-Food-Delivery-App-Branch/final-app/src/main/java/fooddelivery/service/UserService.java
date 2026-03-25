package fooddelivery.service;

import fooddelivery.user.User;
import fooddelivery.user.Customer;
import fooddelivery.user.Driver;
import fooddelivery.user.RestaurantOwner;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
                System.out.println("Account creation failed. Email already exists.");
                return;
            }
        }
        users.add(user);
        System.out.println("Account created for: " + user.getName());
    }

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public Customer registerCustomer(String name, String email, String password, String address) {
        Customer c = new Customer();
        c.setId("c" + System.currentTimeMillis());
        c.setName(name);
        c.setEmail(email);
        c.setPassword(password);
        c.setAddress(address);
        registerUser(c);
        return c;
    }

    public Driver registerDriver(String name, String email, String password, String vehicleType) {
        Driver d = new Driver();
        d.setId("d" + System.currentTimeMillis());
        d.setName(name);
        d.setEmail(email);
        d.setPassword(password);
        d.setVehicleType(vehicleType);
        registerUser(d);
        return d;
    }

    public RestaurantOwner registerManager(String name, String email, String password) {
        RestaurantOwner o = new RestaurantOwner();
        o.setId("o" + System.currentTimeMillis());
        o.setName(name);
        o.setEmail(email);
        o.setPassword(password);
        registerUser(o);
        return o;
    }

    public void updateUser(User user) {}
}
