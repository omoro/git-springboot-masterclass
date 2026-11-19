package org.omoro.taskmanagement.model;

import jakarta.validation.constraints.NotBlank;

public record Task(
        Long id,

        @NotBlank(message = "Task title cannot be blank")
        String title,

        boolean completed) {
}
