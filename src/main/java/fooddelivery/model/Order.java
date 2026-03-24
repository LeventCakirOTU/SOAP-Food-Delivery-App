package fooddelivery.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private String orderId;
    private String customerId;
    private String restaurantId;
    private Map<MenuItem, Integer> items = new HashMap<>();
    private double totalCost;
    private String status;

    public Order() {}

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public Map<MenuItem, Integer> getItems() { return items; }
    public void setItems(Map<MenuItem, Integer> items) { this.items = items; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // add to cart
    public void addItem(MenuItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    // remove from cart
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    // total price
    public void calculateTotal() {
        totalCost = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
    }
}