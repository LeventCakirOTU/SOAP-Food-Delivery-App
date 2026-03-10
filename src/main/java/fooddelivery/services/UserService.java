package fooddelivery.services;

import fooddelivery.users.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = new ArrayList<>();
    }

    public void registerUser(User user) {
        // Prevent duplicate emails
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
                System.out.println("Login successful: " + user.getName());
                return user;
            }
        }
        return null;
    }

    public void updateUser(User user) {}
}