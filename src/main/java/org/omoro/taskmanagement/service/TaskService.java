package org.omoro.taskmanagement.service;

import org.omoro.taskmanagement.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    public List<Task> getTasks() {
        return List.of(
                new Task(1L, "Learn Spring Boot", false),
                new Task(2L, "Learn REST APIs", true)
        );
    }
}
