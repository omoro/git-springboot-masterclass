package org.omoro.taskmanagement.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        //.andExpect(content().json("[]"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Arrange: Create a task
        MvcResult result = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "title": "Lean MockMvc",
                    "completed": false,
                    "priority": "HIGH"
                }
                """)).andDo(print()).andExpect(status().isCreated()).andReturn();

        // Extract th generated ID from the response
        String location = result.getResponse().getHeader("Location");
        long taskId = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));

        // Act & Assert: Retrieve the task
        mockMvc.perform(get("/api/tasks/" + taskId)).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.title").value("Lean MockMvc")).andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        mockMvc.perform(get("/api/tasks/999999")).andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404)).andExpect(jsonPath("$.error").value("Not Found")).andExpect(jsonPath("$.message").value("Task with ID 999999 was not found"));
    }

    @Test
    void shouldUpdateTask() throws Exception {

        // Arrange: Create a task
        MvcResult result = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "title": "Original title",
                    "completed": false,
                    "priority": "HIGH"
                }
                """)).andExpect(status().isCreated()).andReturn();

        String location = result.getResponse().getHeader("Location");
        long taskId = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));

        // Act: Update the task
        mockMvc.perform(put("/api/tasks/" + taskId).contentType(MediaType.APPLICATION_JSON).content("""
                        {
                            "title": "Updated title",
                            "completed": true,
                            "priority": "LOW"
                        }
                        """)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(taskId)).andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.completed").value(true))
                .andExpect(jsonPath("$.priority").value("LOW"));
    }

    @Test
    void shouldDeleteTask() throws Exception {

        // Arrange: Create a task
        MvcResult result = mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "title": "Task to delete",
                    "completed": false,
                    "priority": "HIGH"
                }
                """)).andExpect(status().isCreated()).andReturn();

        // Extract generated ID
        String location = result.getResponse().getHeader("Location");
        long taskId = Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

        // Act: Delete the task
        mockMvc.perform(delete("/api/tasks/" + taskId)).andExpect(status().isNoContent());

        // Assert: Confirm the task no longer exists
        mockMvc.perform(get("/api/tasks/" + taskId)).andExpect(status().isNotFound());
    }
}
