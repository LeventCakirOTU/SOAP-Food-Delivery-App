package fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;
    private String name;
    private Menu menu = new Menu();
    private Location location;
    private List<String> activeOrders = new ArrayList<>();
    private List<Integer> ratings = new ArrayList<>();
    private String openTime;
    private String closeTime;

    public Restaurant() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Menu getMenu() { return menu; }
    public void setMenu(Menu menu) { this.menu = menu; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public List<String> getActiveOrders() { return activeOrders; }
    public void setActiveOrders(List<String> activeOrders) { this.activeOrders = activeOrders; }

    public void addRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            ratings.add(rating);
        }
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;

        int sum = 0;
        for (int r : ratings) sum += r;

        return (double) sum / ratings.size();
    }

    public String getOpenTime() { return openTime; }
    public void setOpenTime(String openTime) { this.openTime = openTime; }

    public String getCloseTime() { return closeTime; }
    public void setCloseTime(String closeTime) { this.closeTime = closeTime; }

    public void addMenuItem(MenuItem item) {
        menu.addItem(item);
        System.out.println("Added item: " + item.getName());
    }

    public void removeMenuItem(String itemId) {
        menu.removeItem(itemId);
    }
}