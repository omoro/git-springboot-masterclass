package org.omoro.taskmanagement.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.omoro.taskmanagement.exception.TaskNotFoundException;
import org.omoro.taskmanagement.model.Task;
import org.omoro.taskmanagement.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Test
    void shouldReturnAllTasks() {

        // Arrange: Create a mocked repository
        TaskRepository repository = mock(TaskRepository.class);

        Task task = new Task(null, "Learn JUnit", false);

        when(repository.findAll()).thenReturn(List.of(task));

        // Create the service using the mocked repository
        TaskService taskService = new TaskService(repository);

        // Act: Call the method we want to test
        List<Task> result = taskService.getTasks();

        // Assert: verify teh result
        assertEquals(1, result.size());
        assertEquals("Learn JUnit", result.getFirst().getTitle());

        verify(repository).findAll();
    }

    @Test
    void shouldReturnTaskById() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        Task task = new Task(1L, "Learn Mockito", false);

        when(repository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskService taskService = new TaskService(repository);

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Learn Mockito", result.getTitle());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        TaskService taskService = new TaskService(repository);

        //Act & Assert
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(99L)
        );
    }

    @Test
    void shouldAddTask() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        Task task = new Task(null, "Learn unit testing", false);
        Task savedTask = new Task(1L, "Learn unit testing", false);

        when(repository.save(task)).thenReturn(savedTask);

        TaskService taskService = new TaskService(repository);

        // Act
        Task result = taskService.addTask(task);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Learn unit testing", result.getTitle());

        verify(repository).save(task);
    }

    @Test
    void shouldUpdateTask() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        Task existingTask = new Task(1L, "Old title", false);
        Task updatedTask = new Task(null, "New title", true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(repository.save(existingTask))
                .thenReturn(existingTask);

        TaskService taskService = new TaskService(repository);

        // Act
        Task result = taskService.updateTask(1L, updatedTask);

        // Asset
        assertEquals("New title", result.getTitle());
        assertTrue(result.isCompleted());

        verify(repository).findById(1L);
        verify(repository).save(existingTask);
    }

    @Test
    void shouldDeleteTask() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        when(repository.existsById(1L)).thenReturn(true);

        TaskService taskService = new TaskService(repository);

        // Act
        boolean result = taskService.deleteTaskById(1L);

        // Assert
        assertTrue(result);
        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteNonExistingTask() {

        // Arrange
        TaskRepository repository = mock(TaskRepository.class);

        when(repository.existsById(99L)).thenReturn(false);

        TaskService taskService = new TaskService(repository);

        // Act
        boolean result = taskService.deleteTaskById(99L);

        //Assert
        assertFalse(result);
        verify(repository).existsById(99L);
        verify(repository, Mockito.never())
                .deleteById(99L);

    }
}
