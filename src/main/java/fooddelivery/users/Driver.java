package fooddelivery.users;

import fooddelivery.model.DeliveryTask;
import java.util.ArrayList;
import java.util.List;

public class Driver extends User {

    private String vehicleType;
    private boolean available;
    private List<DeliveryTask> tasks = new ArrayList<>();

    public Driver() {}

    public List<DeliveryTask> getTasks() { return tasks; }

    public void acceptTask(DeliveryTask task) {
        task.setDriverId(this.id);
        task.setStatus("ACCEPTED");
        tasks.add(task);
        System.out.println("Accepted task: " + task.getTaskId());
    }

    public void rejectTask(DeliveryTask task) {
        task.setStatus("REJECTED");
        System.out.println("Rejected task: " + task.getTaskId());
    }
}