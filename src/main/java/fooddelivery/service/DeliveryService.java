package fooddelivery.service;

import fooddelivery.model.DeliveryTask;
import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    private final List<DeliveryTask> tasks = new ArrayList<>();

    public void assignTask(DeliveryTask task) {
        tasks.add(task);
    }

    public void updateTaskStatus(String taskId, String status) {
        for (DeliveryTask t : tasks) {
            if (t.getId().equals(taskId)) {
                t.setStatus(status);
                return;
            }
        }
    }

    public DeliveryTask getTask(String taskId) {
        for (DeliveryTask t : tasks) {
            if (t.getId().equals(taskId)) return t;
        }
        return null;
    }

    public List<DeliveryTask> getTasksByDriver(String driverId) {
        List<DeliveryTask> result = new ArrayList<>();
        for (DeliveryTask t : tasks) {
            if (driverId.equals(t.getDriverId())) result.add(t);
        }
        return result;
    }

    public List<DeliveryTask> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
