package com.fooddelivery.ui;

import com.fooddelivery.service.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // name constants
    public static final String SCREEN_LANDING          = "LANDING";
    public static final String SCREEN_LOGIN            = "LOGIN";
    public static final String SCREEN_REGISTER_CUSTOMER = "REGISTER_CUSTOMER";
    public static final String SCREEN_REGISTER_DRIVER   = "REGISTER_DRIVER";
    public static final String SCREEN_REGISTER_MANAGER  = "REGISTER_MANAGER";
    public static final String SCREEN_RESTAURANT_LIST   = "RESTAURANT_LIST";
    public static final String SCREEN_ADD_RESTAURANT    = "ADD_RESTAURANT";
    public static final String SCREEN_CUSTOMER_DASHBOARD = "CUSTOMER_DASHBOARD";
    public static final String SCREEN_DRIVER_DASHBOARD   = "DRIVER_DASHBOARD";
    public static final String SCREEN_MANAGER_DASHBOARD  = "MANAGER_DASHBOARD";

    // service (shared by all)
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public MainFrame(UserService userService, RestaurantService restaurantService) {
        this.userService       = userService;
        this.restaurantService = restaurantService;
        this.orderService      = new OrderService();

        setTitle("SOAP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setMinimumSize(new Dimension(800, 540));
        setLocationRelativeTo(null);
        getContentPane().setBackground(AppColors.BG_DARK);

        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
        cardPanel.setBackground(AppColors.BG_DARK);

        // Register all screens
        cardPanel.add(new LandingScreen(this),                            SCREEN_LANDING);
        cardPanel.add(new LoginScreen(this, userService),                 SCREEN_LOGIN);
        cardPanel.add(new RegisterCustomerScreen(this, userService),      SCREEN_REGISTER_CUSTOMER);
        cardPanel.add(new RegisterDriverScreen(this, userService),        SCREEN_REGISTER_DRIVER);
        cardPanel.add(new RegisterManagerScreen(this, userService),       SCREEN_REGISTER_MANAGER);
        cardPanel.add(new RestaurantListScreen(this, restaurantService),  SCREEN_RESTAURANT_LIST);
        cardPanel.add(new AddRestaurantScreen(this, restaurantService, userService), SCREEN_ADD_RESTAURANT);
        cardPanel.add(new CustomerDashboardScreen(this),                  SCREEN_CUSTOMER_DASHBOARD);
        cardPanel.add(new DriverDashboardScreen(this),                    SCREEN_DRIVER_DASHBOARD);
        cardPanel.add(new ManagerDashboardScreen(this),                   SCREEN_MANAGER_DASHBOARD);

        add(cardPanel);
        showScreen(SCREEN_LANDING);
        setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    // so screens can share
    public UserService getUserService()           { return userService; }
    public RestaurantService getRestaurantService() { return restaurantService; }
    public OrderService getOrderService()         { return orderService; }
}
