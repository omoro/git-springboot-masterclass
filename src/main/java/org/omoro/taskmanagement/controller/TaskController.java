package org.omoro.taskmanagement.controller;

import org.omoro.taskmanagement.model.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @GetMapping
    public List<Task> getTasks() {
      return List.of(
              new Task(1L, "Learn Spring Boot", false),
              new Task(2L, "Learn REST APIs", true)
      );
    }
}
