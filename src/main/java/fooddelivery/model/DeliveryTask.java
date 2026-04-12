package fooddelivery.model;

public class DeliveryTask {

    private String id;
    private String orderId;
    private String driverId;
    private String status;
    private String pickupLocation;
    private String dropoffLocation;

    public DeliveryTask() {}

    // getId/setId
    public String getId()              { return id; }
    public void   setId(String id)     { this.id = id; }

    // Alias used by unit tests
    public String getTaskId()              { return id; }
    public void   setTaskId(String taskId) { this.id = taskId; }

    public String getOrderId()                 { return orderId; }
    public void   setOrderId(String orderId)   { this.orderId = orderId; }

    public String getDriverId()                { return driverId; }
    public void   setDriverId(String driverId) { this.driverId = driverId; }

    public String getStatus()              { return status; }
    public void   setStatus(String status) { this.status = status; }

    public String getPickupLocation()                   { return pickupLocation; }
    public void   setPickupLocation(String location)    { this.pickupLocation = location; }

    public String getDropoffLocation()                  { return dropoffLocation; }
    public void   setDropoffLocation(String location)   { this.dropoffLocation = location; }
}
