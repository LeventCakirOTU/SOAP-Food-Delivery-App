package fooddelivery.service;

import fooddelivery.model.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public void placeOrder(Order order) {
        order.setStatus("PENDING");
        orders.add(order);
    }

    public void cancelOrder(String orderId) {
        for (Order o : orders) {
            if (o.getId().equals(orderId)) {
                o.setStatus("CANCELLED");
                return;
            }
        }
    }

    public Order getOrder(String orderId) {
        for (Order o : orders) {
            if (o.getId().equals(orderId)) return o;
        }
        return null;
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (customerId.equals(o.getCustomerId())) result.add(o);
        }
        return result;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
}
