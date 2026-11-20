package org.omoro.taskmanagement.service;

import org.omoro.taskmanagement.exception.TaskNotFoundException;
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
        return tasks.stream()
                .filter(task -> task.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Long id, Task task) {

        for (int i =0; i < tasks.size(); i++) {
            if (tasks.get(i).id().equals(id)) {
                Task task1 = new Task(
                        task.id(),
                        task.title(),
                        task.completed()
                );
                tasks.set(i, task1);
                return task1;
            }
        }

        return null;
    }

    public boolean deleteTaskById(Long id) {
        return tasks.removeIf(task -> task.id().equals(id));
    }
}
