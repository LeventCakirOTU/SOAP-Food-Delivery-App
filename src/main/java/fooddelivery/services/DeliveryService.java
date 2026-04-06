package fooddelivery.services;

import fooddelivery.model.DeliveryTask;

import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    private List<DeliveryTask> tasks = new ArrayList<>();

    public DeliveryService() {}

    public void addTask(DeliveryTask task) {
        tasks.add(task);
    }

    public List<DeliveryTask> getAllTasks() {
        return tasks;
    }

    public DeliveryTask getTask(String taskId) {
        for (DeliveryTask t : tasks) {
            if (t.getTaskId().equals(taskId)) return t;
        }
        return null;
    }
}