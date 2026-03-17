package fooddelivery.model;

import java.util.List;

public class Order {

    private String         id;
    private String         customerId;
    private String         restaurantId;
    private List<MenuItem> items;
    private String         status;
    private double         totalPrice;

    public Order() {}

    public String getId()                   { return id; }
    public void   setId(String id)          { this.id = id; }

    public String getCustomerId()                       { return customerId; }
    public void   setCustomerId(String customerId)      { this.customerId = customerId; }

    public String getRestaurantId()                         { return restaurantId; }
    public void   setRestaurantId(String restaurantId)      { this.restaurantId = restaurantId; }

    public List<MenuItem> getItems()                    { return items; }
    public void           setItems(List<MenuItem> items){ this.items = items; }

    public String getStatus()               { return status; }
    public void   setStatus(String status)  { this.status = status; }

    public double getTotalPrice()               { return totalPrice; }
    public void   setTotalPrice(double price)   { this.totalPrice = price; }
}
