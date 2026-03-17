package com.fooddelivery.service;

import com.fooddelivery.model.DeliveryTask;
import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    private final List<DeliveryTask> tasks = new ArrayList<>();

    public void assignTask(DeliveryTask task)           {}
    public void updateTaskStatus(String taskId, String status) {}
    public DeliveryTask getTask(String taskId)          { return null; }
    public List<DeliveryTask> getTasksByDriver(String driverId) { return new ArrayList<>(); }
}
