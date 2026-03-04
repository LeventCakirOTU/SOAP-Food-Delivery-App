package fooddelivery.model;

public class DeliveryTask {

    private String taskId;
    private String orderId;
    private String driverId;
    private String pickupLocation;
    private String dropoffLocation;
    private String status; // pending, accepted, in-transit, completed

    public DeliveryTask() {}

    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void updateStatus(String newStatus) {}
}