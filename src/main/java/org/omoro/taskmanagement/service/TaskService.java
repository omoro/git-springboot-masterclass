package org.omoro.taskmanagement.service;

import org.omoro.taskmanagement.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        tasks.add(new Task(1L, "Learn Spring Boot", false));
        tasks.add(new Task(2L, "Learn REST APIs", true));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public Task getTaskById(Long id) {
        return tasks.stream().filter(task -> task.id().equals(id)).findFirst().orElse(null);
    }
}
