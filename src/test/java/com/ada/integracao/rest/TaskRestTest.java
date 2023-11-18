package com.ada.integracao.rest;

import com.ada.integracao.Task;
import com.ada.integracao.TaskRepository;
import com.ada.integracao.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask() throws Exception {
        mockMvc.perform(post("/tasks/save")
                        .contentType("application/json")
                        .content("{\"description\":\"Minha Tarefa\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description", is("Minha Tarefa")));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setDescription("Task 1");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setDescription("Task 2");
        taskRepository.save(task2);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
//        DESAFIO        .andExpect(jsonPath("$.[*]", contains(task1)))
        ;
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = new Task();
        task.setDescription("Task to delete");
        task = taskRepository.save(task);

        Long taskId = task.getId();

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isOk());

        // Verifica se a tarefa foi removida do banco de dados
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
