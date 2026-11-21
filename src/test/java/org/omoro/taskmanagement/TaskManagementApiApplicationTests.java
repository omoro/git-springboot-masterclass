package org.omoro.taskmanagement;

import org.junit.jupiter.api.Test;
import org.omoro.taskmanagement.model.Task;
import org.omoro.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskManagementApiApplicationTests {

	@Autowired
	private TaskRepository taskRepository;

	@Test
	void shouldSaveAndRetrieveTask() {

		// Arrange
		Task task = new Task(null, "Integration testing", false);

		// Act
		Task savedTask = taskRepository.save(task);

		// Rerieve the task using its generated ID
		Task retrievedTask = taskRepository.findById(savedTask.getId()).orElseThrow();

		// Assert
		assertNotNull(savedTask.getId());
		assertEquals("Integration testing", retrievedTask.getTitle());
		assertFalse(retrievedTask.isCompleted());
	}
}
