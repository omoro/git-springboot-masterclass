package org.omoro.taskmanagement.controller;

import org.omoro.taskmanagement.model.Task;
import org.omoro.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.addTask(task);

        return ResponseEntity
                .created(URI.create("/api/tasks/" + createdTask.id()))
                .body(createdTask);
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
