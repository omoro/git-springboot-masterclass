package org.omoro.taskmanagement.controller;

import org.omoro.taskmanagement.model.Task;
import org.omoro.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
      return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {

        if (task.id() != null && !task.id().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        Task updatedTask = taskService.updateTask(id, task);

        if(updatedTask == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {

        boolean result = taskService.deleteTaskById(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
