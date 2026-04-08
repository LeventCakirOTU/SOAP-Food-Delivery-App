package fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String   id;
    private String   name;
    private String   category;
    private String   address;
    private String   openHours;
    private double   rating;
    private Menu     menu = new Menu();
    private Location location;
    private List<String>  activeOrders = new ArrayList<>();
    private List<Integer> ratings      = new ArrayList<>();  // dynamic ratings list

    public Restaurant() {}
    
    public String getId()               { return id; }
    public void   setId(String id)      { this.id = id; }

    public String getName()             { return name; }
    public void   setName(String name)  { this.name = name; }

    public String getCategory()                 { return category; }
    public void   setCategory(String category)  { this.category = category; }

    public String getAddress()              { return address; }
    public void   setAddress(String address){ this.address = address; }

    public String getOpenHours()                { return openHours; }
    public void   setOpenHours(String openHours){ this.openHours = openHours; }

    public double getRating()               { return rating; }
    public void   setRating(double rating)  { this.rating = rating; }

    public Menu getMenu()               { return menu; }
    public void setMenu(Menu menu)      { this.menu = menu; }

    public Location getLocation()               { return location; }
    public void     setLocation(Location loc)   { this.location = loc; }

    public List<String> getActiveOrders()                       { return activeOrders; }
    public void         setActiveOrders(List<String> orders)    { this.activeOrders = orders; }

    public void addRating(int stars) {
        if (stars >= 1 && stars <= 5) ratings.add(stars);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return rating; 
        int sum = 0;
        for (int r : ratings) sum += r;
        return (double) sum / ratings.size();
    }

    public void addMenuItem(MenuItem item) {
        menu.addItem(item);
        System.out.println("Added item: " + item.getName() + " to " + this.name);
    }

    public void removeMenuItem(String itemId) {
        menu.removeItem(itemId);
    }
    public void addActiveOrder(String orderId) {
        this.activeOrders.add(orderId);
    }
}
