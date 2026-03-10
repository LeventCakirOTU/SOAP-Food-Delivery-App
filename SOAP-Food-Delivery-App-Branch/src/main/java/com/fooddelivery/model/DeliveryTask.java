package com.fooddelivery.model;

public class DeliveryTask {

    private String   id;
    private String   orderId;
    private String   driverId;
    private String   status;
    private Location pickupLocation;
    private Location dropoffLocation;

    public DeliveryTask() {}

    public String getId()               { return id; }
    public void   setId(String id)      { this.id = id; }

    public String getOrderId()                  { return orderId; }
    public void   setOrderId(String orderId)    { this.orderId = orderId; }

    public String getDriverId()                 { return driverId; }
    public void   setDriverId(String driverId)  { this.driverId = driverId; }

    public String getStatus()               { return status; }
    public void   setStatus(String status)  { this.status = status; }

    public Location getPickupLocation()                     { return pickupLocation; }
    public void     setPickupLocation(Location location)    { this.pickupLocation = location; }

    public Location getDropoffLocation()                        { return dropoffLocation; }
    public void     setDropoffLocation(Location location)       { this.dropoffLocation = location; }
}
