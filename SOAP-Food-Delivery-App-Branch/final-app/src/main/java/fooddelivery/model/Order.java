package fooddelivery.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private String         id;
    private String         customerId;
    private String         restaurantId;
    // Changed to Map so your new logic actually works
    private Map<MenuItem, Integer> items = new HashMap<>();
    private String         status;
    private double         totalPrice;

    public Order() {}

    public String getId()              { return id; }
    public void   setId(String id)     { this.id = id; }

    public String getOrderId()               { return id; }
    public void   setOrderId(String orderId) { this.id = orderId; }

    public String getCustomerId()                      { return customerId; }
    public void   setCustomerId(String customerId)     { this.customerId = customerId; }

    public String getRestaurantId()                        { return restaurantId; }
    public void   setRestaurantId(String restaurantId)     { this.restaurantId = restaurantId; }

    // Updated return type to Map to match the variable
    public Map<MenuItem, Integer> getItems() { return items; }
    public void setItems(Map<MenuItem, Integer> items) { this.items = items; }

    public String getStatus()              { return status; }
    public void   setStatus(String status) { this.status = status; }

    public double getTotalPrice()              { return totalPrice; }
    public void   setTotalPrice(double price)  { this.totalPrice = price; }

    public double getTotalCost()              { return totalPrice; }
    public void   setTotalCost(double price)  { this.totalPrice = price; }

    // --- YOUR ADDED LOGIC ---

    // Add to cart
    // Add item to cart
    public void addItem(MenuItem item, int quantity) {
        if (item == null || quantity <= 0) return;

        items.put(item, items.getOrDefault(item, 0) + quantity);
        calculateTotal();
    }

    // Remove from cart
    public void removeItem(MenuItem item) {
        items.remove(item);
        calculateTotal();
    }


    //update quantity
    public void updateItem(MenuItem item, int quantity) {
        if (quantity <= 0) {
            items.remove(item);
        } else {
            items.put(item, quantity);
        }
        calculateTotal();
    }

    // total price
    public void calculateTotal() {
        totalPrice = 0.0;

        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int qty = entry.getValue();
            totalPrice += item.getPrice() * qty;
        }
    }
}