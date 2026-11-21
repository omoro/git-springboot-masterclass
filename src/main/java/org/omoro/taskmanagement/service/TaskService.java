package org.omoro.taskmanagement.service;

import org.omoro.taskmanagement.exception.TaskNotFoundException;
import org.omoro.taskmanagement.model.Task;
import org.omoro.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Long id, Task updateTask) {
        Task task = getTaskById(id);

        task.setTitle(updateTask.getTitle());
        task.setCompleted(updateTask.isCompleted());
        task.setPriority(updateTask.getPriority());

        return taskRepository.save(task);
    }

    public boolean deleteTaskById(Long id) {
        if(!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
    }
}
