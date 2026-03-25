package fooddelivery.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryTaskTest {

  private DeliveryTask deliveryTask;

  @Before
  public void setUp() {
    deliveryTask = new DeliveryTask();
  }

  @Test
  public void testSetGetTaskId() {
    deliveryTask.setTaskId("task1");
    assertEquals("task1", deliveryTask.getTaskId());
    deliveryTask.setTaskId("task2");
    assertEquals("task2", deliveryTask.getTaskId());
    deliveryTask.setTaskId("");
    assertEquals("", deliveryTask.getTaskId());
  }

  @Test
  public void testSetGetOrderId() {
    deliveryTask.setOrderId("order1");
    assertEquals("order1", deliveryTask.getOrderId());
    deliveryTask.setOrderId("order2");
    assertEquals("order2", deliveryTask.getOrderId());
    deliveryTask.setOrderId("");
    assertEquals("", deliveryTask.getOrderId());
  }

  @Test
  public void testSetGetDriverId() {
    deliveryTask.setDriverId("driver1");
    assertEquals("driver1", deliveryTask.getDriverId());
    deliveryTask.setDriverId("driver2");
    assertEquals("driver2", deliveryTask.getDriverId());
    deliveryTask.setDriverId("");
    assertEquals("", deliveryTask.getDriverId());
  }

  @Test
  public void testSetGetPickupLocation() {
    deliveryTask.setPickupLocation("pickupLocation1");
    assertEquals("pickupLocation1", deliveryTask.getPickupLocation());
    deliveryTask.setPickupLocation("pickupLocation2");
    assertEquals("pickupLocation2", deliveryTask.getPickupLocation());
    deliveryTask.setPickupLocation("");
    assertEquals("", deliveryTask.getPickupLocation());
  }

  @Test
  public void testSetGetDropoffLocation() {
    deliveryTask.setDropoffLocation("dropoffLocation1");
    assertEquals("dropoffLocation1", deliveryTask.getDropoffLocation());
    deliveryTask.setDropoffLocation("dropoffLocation2");
    assertEquals("dropoffLocation2", deliveryTask.getDropoffLocation());
    deliveryTask.setDropoffLocation("");
    assertEquals("", deliveryTask.getDropoffLocation());
  }

  @Test
  public void testSetGetStatus() {
    deliveryTask.setStatus("pending");
    assertEquals("pending", deliveryTask.getStatus());
    deliveryTask.setStatus("accepted");
    assertEquals("accepted", deliveryTask.getStatus());
    deliveryTask.setStatus("");
    assertEquals("", deliveryTask.getStatus());
  }
}
