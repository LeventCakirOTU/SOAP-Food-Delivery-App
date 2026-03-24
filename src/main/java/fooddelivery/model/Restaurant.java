package fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;
    private String name;
    private Menu menu = new Menu();
    private Location location;
    private List<String> activeOrders = new ArrayList<>();

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

    public void addMenuItem(MenuItem item) {
        menu.addItem(item);
        System.out.println("Added item: " + item.getName());
    }

    public void removeMenuItem(String itemId) {
        menu.removeItem(itemId);
    }
}