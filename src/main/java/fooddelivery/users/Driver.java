package fooddelivery.users;

import fooddelivery.model.DeliveryTask;
import java.util.List;

public class Driver extends User {

    private String vehicleType;
    private boolean available;
    private List<DeliveryTask> tasks;

    public Driver() {}

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public List<DeliveryTask> getTasks() { return tasks; }
    public void setTasks(List<DeliveryTask> tasks) { this.tasks = tasks; }

    public void acceptTask(DeliveryTask task) {}
    public void updateLocation() {}
    public void markTaskCompleted(String taskId) {}
}