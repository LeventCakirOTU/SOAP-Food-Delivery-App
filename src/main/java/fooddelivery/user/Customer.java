package fooddelivery.user;

import fooddelivery.model.MenuItem;
import fooddelivery.model.Order;
import java.util.List;
import java.util.ArrayList;

public class Customer extends User {

    private String address;
    private double latitude;
    private double longitude;
    private List<Order> orderHistory = new ArrayList<>();
    private Order currentOrder = new Order();

    public Customer() {}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getLatitude()              { return latitude; }
    public void   setLatitude(double lat)    { this.latitude = lat; }

    public double getLongitude()             { return longitude; }
    public void   setLongitude(double lon)   { this.longitude = lon; }

    public boolean hasCoordinates()          { return latitude != 0.0 || longitude != 0.0; }

    public List<Order> getOrderHistory() { return orderHistory; }
    public void setOrderHistory(List<Order> orderHistory) { this.orderHistory = orderHistory; }

    public Order getCurrentOrder() { return currentOrder; }

    // add to cart
    public void addToCart(MenuItem item, int quantity) {
        currentOrder.addItem(item, quantity);
        System.out.println("Added to cart: " + item.getName());
    }

    // checkout
    public void placeOrder(Order order) {
        order.calculateTotal();
        order.setStatus("PLACED");

        orderHistory.add(order);

        currentOrder = new Order(); // reset cart

        System.out.println("Order placed! Total: $" + order.getTotalPrice());
    }

    public void browseRestaurants() {}
    public void viewOrderStatus(String orderId) {}
}
