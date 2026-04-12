package fooddelivery.ui;

import fooddelivery.model.Restaurant;
import fooddelivery.service.RestaurantService;
import fooddelivery.user.Customer;
import javax.swing.*;
import java.awt.*;
import java.util.List;


//Shows list of all open restaurants.
public class RestaurantListScreen extends JPanel {

    private final JPanel listPanel;
    private final RestaurantService restaurantService;
    private final MainFrame frame;

    public RestaurantListScreen(MainFrame frame, RestaurantService restaurantService) {
        this.frame             = frame;
        this.restaurantService = restaurantService;
        setLayout(new BorderLayout());
        setBackground(AppColors.BG_DARK);
        add(buildTopBar(frame), BorderLayout.NORTH);

        // Scrollable restaurant cards
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(AppColors.BG_DARK);
        listPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 16, 32));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBackground(AppColors.BG_DARK);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppColors.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        refreshList();
    }

    //called when list changes
    public void refreshList() {
        listPanel.removeAll();

        List<Restaurant> restaurants;
        double customerLat = 0, customerLng = 0;
        boolean hasCoords = false;

        if (frame.getCurrentUser() instanceof Customer customer && customer.hasCoordinates()) {
            customerLat = customer.getLatitude();
            customerLng = customer.getLongitude();
            hasCoords = true;
            restaurants = restaurantService.findNearbyRestaurants(customerLat, customerLng);
        } else {
            restaurants = restaurantService.getAll();
        }

        if (restaurants.isEmpty()) {
            JLabel empty = UIHelper.label("No restaurants available yet.", AppColors.FONT_BODY, AppColors.TEXT_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(empty);
        } else {
            for (Restaurant r : restaurants) {
                double dist = hasCoords ? distanceKm(customerLat, customerLng,
                        r.getLocation().getLatitude(), r.getLocation().getLongitude()) : -1;
                listPanel.add(buildRestaurantCard(r, dist));
                listPanel.add(Box.createVerticalStrut(12));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double dx = (lat1 - lat2) * 111.0;
        double dy = (lon1 - lon2) * 111.0 * Math.cos(Math.toRadians((lat1 + lat2) / 2.0));
        return Math.sqrt(dx * dx + dy * dy);
    }

    private JPanel buildRestaurantCard(Restaurant restaurant, double distanceKm) {
        JPanel card = UIHelper.cardPanel();
        card.setLayout(new BorderLayout(12, 0));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left: name + details
        JPanel info = new JPanel();
        info.setBackground(AppColors.BG_CARD);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel name = UIHelper.label(restaurant.getName(), AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY);
        String detailText = restaurant.getCategory() + "  •  " + restaurant.getAddress() + "  •  " + restaurant.getOpenHours();
        if (distanceKm >= 0) detailText += String.format("  •  %.1f km away", distanceKm);
        JLabel details = UIHelper.label(detailText, AppColors.FONT_SMALL, AppColors.TEXT_MUTED);

        info.add(name);
        info.add(Box.createVerticalStrut(4));
        info.add(details);

        // Right: rating + order button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(AppColors.BG_CARD);
        JLabel rating = UIHelper.label(String.format("★ %.1f", restaurant.getAverageRating()), AppColors.FONT_BODY, AppColors.ACCENT);
        JButton orderBtn = UIHelper.primaryButton("View Menu");
        orderBtn.setPreferredSize(new Dimension(110, 34));
        orderBtn.addActionListener(e -> frame.showMenu(restaurant));

        right.add(rating);
        right.add(orderBtn);

        card.add(info, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);
        return card;
    }

    private JPanel buildTopBar(MainFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        bar.setBackground(AppColors.BG_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        JButton back = UIHelper.secondaryButton("<- Back");
        back.setPreferredSize(new Dimension(90, 34));
        back.addActionListener(e -> frame.showScreen(MainFrame.SCREEN_CUSTOMER_DASHBOARD));
        JButton refresh = UIHelper.secondaryButton("r Refresh");
        refresh.setPreferredSize(new Dimension(100, 34));
        refresh.addActionListener(e -> refreshList());
        bar.add(back);
        bar.add(UIHelper.label("Restaurants", AppColors.FONT_HEADING, AppColors.TEXT_PRIMARY));
        bar.add(Box.createHorizontalGlue());
        bar.add(refresh);
        return bar;
    }
}
