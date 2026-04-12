package fooddelivery.ui;

import fooddelivery.model.Order;
import fooddelivery.service.*;
import fooddelivery.user.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Screen name constants
    public static final String SCREEN_LANDING            = "LANDING";
    public static final String SCREEN_LOGIN              = "LOGIN";
    public static final String SCREEN_REGISTER_CUSTOMER  = "REGISTER_CUSTOMER";
    public static final String SCREEN_REGISTER_DRIVER    = "REGISTER_DRIVER";
    public static final String SCREEN_REGISTER_MANAGER   = "REGISTER_MANAGER";
    public static final String SCREEN_RESTAURANT_LIST    = "RESTAURANT_LIST";
    public static final String SCREEN_ADD_RESTAURANT     = "ADD_RESTAURANT";
    public static final String SCREEN_CUSTOMER_DASHBOARD = "CUSTOMER_DASHBOARD";
    public static final String SCREEN_DRIVER_DASHBOARD   = "DRIVER_DASHBOARD";
    public static final String SCREEN_MANAGER_DASHBOARD  = "MANAGER_DASHBOARD";
    public static final String SCREEN_ADMIN_DASHBOARD    = "ADMIN_DASHBOARD";    // NEW
    public static final String SCREEN_MENU               = "MENU";
    public static final String SCREEN_CART               = "CART";
    public static final String SCREEN_ORDER_HISTORY      = "ORDER_HISTORY";

    // Services
    private final UserService        userService;
    private final RestaurantService  restaurantService;
    private final OrderService       orderService;
    private final DeliveryService    deliveryService;
    private final RatingService      ratingService;

    // State
    private User   currentUser;
    private Order  currentCart    = new Order();
    private String previousScreen = SCREEN_LANDING;

    // Layout
    private final CardLayout cardLayout;
    private final JPanel     cardPanel;

    // Screens that need refresh calls
    private MenuScreen            menuScreen;
    private CartScreen            cartScreen;
    private OrderHistoryScreen    orderHistoryScreen;
    private DriverDashboardScreen driverDashboard;
    private AdminDashboardScreen  adminDashboard;      // NEW

    public MainFrame(UserService userService, RestaurantService restaurantService) {
        this.userService       = userService;
        this.restaurantService = restaurantService;
        this.orderService      = new OrderService();
        this.deliveryService   = new DeliveryService();
        this.ratingService     = new RatingService();

        setTitle("SOAP — Food Delivery");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setMinimumSize(new Dimension(800, 540));
        setLocationRelativeTo(null);
        getContentPane().setBackground(AppColors.BG_DARK);

        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
        cardPanel.setBackground(AppColors.BG_DARK);

        // Build screens
        cartScreen         = new CartScreen(this, orderService);
        orderHistoryScreen = new OrderHistoryScreen(this, ratingService);
        menuScreen         = new MenuScreen(this);
        driverDashboard    = new DriverDashboardScreen(this, orderService, deliveryService);
        adminDashboard     = new AdminDashboardScreen(this);                         // NEW

        cardPanel.add(new LandingScreen(this),                                       SCREEN_LANDING);
        cardPanel.add(new LoginScreen(this, userService),                            SCREEN_LOGIN);
        cardPanel.add(new RegisterCustomerScreen(this, userService),                 SCREEN_REGISTER_CUSTOMER);
        cardPanel.add(new RegisterDriverScreen(this, userService),                   SCREEN_REGISTER_DRIVER);
        cardPanel.add(new RegisterManagerScreen(this, userService),                  SCREEN_REGISTER_MANAGER);
        cardPanel.add(new RestaurantListScreen(this, restaurantService),             SCREEN_RESTAURANT_LIST);
        cardPanel.add(new AddRestaurantScreen(this, restaurantService, userService), SCREEN_ADD_RESTAURANT);
        cardPanel.add(new CustomerDashboardScreen(this),                             SCREEN_CUSTOMER_DASHBOARD);
        cardPanel.add(driverDashboard,                                               SCREEN_DRIVER_DASHBOARD);
        cardPanel.add(new ManagerDashboardScreen(this),                              SCREEN_MANAGER_DASHBOARD);
        cardPanel.add(adminDashboard,                                                SCREEN_ADMIN_DASHBOARD);  // NEW
        cardPanel.add(menuScreen,         SCREEN_MENU);
        cardPanel.add(cartScreen,         SCREEN_CART);
        cardPanel.add(orderHistoryScreen, SCREEN_ORDER_HISTORY);

        add(cardPanel);
        showScreen(SCREEN_LANDING);
        setVisible(true);
    }

    public void showScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    public void setPreviousScreen(String screen) { this.previousScreen = screen; }

    public void goBack() { showScreen(previousScreen); }

    // cart helper

    public Order getCurrentCart() { return currentCart; }

    public void resetCart() {
        currentCart = new Order();
        if (menuScreen != null) menuScreen.refreshCartBadge();
    }

    // screen accessors

    public CartScreen         getCartScreen()         { return cartScreen; }
    public OrderHistoryScreen getOrderHistoryScreen() { return orderHistoryScreen; }

    public void showMenu(fooddelivery.model.Restaurant restaurant) {
        previousScreen = SCREEN_RESTAURANT_LIST;
        menuScreen.loadRestaurant(restaurant);
        showScreen(SCREEN_MENU);
    }

    public void showDriverDashboard() {
        driverDashboard.refresh(this);
        showScreen(SCREEN_DRIVER_DASHBOARD);
    }

    // service accessors
    public UserService       getUserService()       { return userService; }
    public RestaurantService getRestaurantService() { return restaurantService; }
    public OrderService      getOrderService()      { return orderService; }
    public DeliveryService   getDeliveryService()   { return deliveryService; }
    public RatingService     getRatingService()     { return ratingService; }

    public User getCurrentUser()          { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
}