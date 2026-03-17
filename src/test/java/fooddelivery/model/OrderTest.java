package fooddelivery.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OrderTest {
  private Order order;

  @Before
  public void setUp() {
    order = new Order();
  }

  @Test
  public void testSetGetOrderId() {

    order.setOrderId("order1");
    assertEquals("order1", order.getOrderId());

    order.setOrderId("order2");
    assertEquals("order2", order.getOrderId());

    order.setOrderId("");
    assertEquals("", order.getOrderId());
  }

  @Test
  public void testSetGetCustomerId() {

    order.setCustomerId("customer1");
    assertEquals("customer1", order.getCustomerId());

    order.setCustomerId("customer2");
    assertEquals("customer2", order.getCustomerId());

    order.setCustomerId("");
    assertEquals("", order.getCustomerId());
  }

  @Test
  public void testSetGetRestaurantId() {

    order.setRestaurantId("restaurant1");
    assertEquals("restaurant1", order.getRestaurantId());

    order.setRestaurantId("restaurant2");
    assertEquals("restaurant2", order.getRestaurantId());

    order.setRestaurantId("");
    assertEquals("", order.getRestaurantId());
  }

  @Test
  public void testSetGetItems() {

    ArrayList<MenuItem> items1 = new ArrayList<>();
    MenuItem menuItem1 = new MenuItem();
    items1.add(menuItem1);
    order.setItems(items1);
    assertEquals(items1, order.getItems());

    ArrayList<MenuItem> items2 = new ArrayList<>();
    order.setItems(items2);
    assertEquals(items2, order.getItems());
  }

  @Test
  public void testSetGetTotalCost() {

    order.setTotalCost(1.0);
    assertEquals(1.0, order.getTotalCost(), 0.0);

    order.setTotalCost(0.0);
    assertEquals(0.0, order.getTotalCost(), 0.0);

    order.setTotalCost(-1.0);
    assertEquals(-1.0, order.getTotalCost(), 0.0);
  }

  @Test
  public void testSetGetStatus() {

    order.setStatus("status1");
    assertEquals("status1", order.getStatus());

    order.setStatus("status2");
    assertEquals("status2", order.getStatus());

    order.setStatus("");
    assertEquals("", order.getStatus());
  }

}