package com.nickbarak.taskerapi.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.service.TaskService;
import com.nickbarak.taskerapi.service.UserService;
import com.nickbarak.taskerapi.util.JwtUtility;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskControllerTests {
    
    @Autowired
    private TaskController taskController;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtility jwtUtility;

    private MockMvc mockMvc;

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
            .build();
    }

    @Test
    public void doPost() throws Exception {
        User user = new User("user1", "pass1");
        Task task = new Task("test", new Date(), true, user);
        when(taskService.saveOne(any(Task.class)))
            .thenReturn(task);
        
        when(jwtUtility.extractUsername(any(String.class)))
            .thenReturn("user1");

        when(userService.loadUserByUsername(any(String.class)))
            .thenReturn(user);
        
        mockMvc.perform(post("/task").contentType(MediaType.TEXT_PLAIN)
            .content("some content"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.author").value("user1"));

        verify(taskService).saveOne(any(Task.class));
    }

    @Test
    public void doGet() throws Exception {
        List<Task> tasks = new LinkedList<>();
        User user = new User();
        tasks.add(new Task("test 1", new Date(), false, user));
        tasks.add(new Task("test 2", new Date(), true, user));
        tasks.add(new Task("test 3", new Date(), false, user));
        when(taskService.getAllByUser(any()))
            .thenReturn(tasks);

        mockMvc.perform(get("/task"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)))
            .andExpect(jsonPath("$[1].content").value("test 2"))
            .andExpect(jsonPath("$[1].isComplete").value(true));

        verify(taskService).getAllByUser(any());
    }

    @Test
    public void doGetOne() throws Exception {
        Task task = new Task("test", new Date(), true, new User());
        when(taskService.getOne(1L))
            .thenReturn(Optional.of(task));

        doThrow(new ResourceNotFoundException("Resource not found with id=2"))
            .when(taskService)
            .getOne(2L);

        mockMvc.perform(get("/task/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("test"));

        mockMvc.perform(get("/task/2"))
            .andExpect(status().isNotFound());

        verify(taskService, times(2)).getOne(any(Long.class));
    }

    @Test
    public void doPut() throws Exception {
        Task task = new Task("test", new Date(), true, new User());
        String json = new ObjectMapper().writeValueAsString(task);
        when(taskService.saveOne(any(Task.class)))
            .thenReturn(task);

        mockMvc.perform(put("/task/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());

        mockMvc.perform(put("/task/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("23"))
        .andExpect(status().isBadRequest());

        verify(taskService).saveOne(any(Task.class));
    }

    @Test
    public void doDelete() throws Exception {
        doNothing()
            .when(taskService)
            .deleteOne(1L);

        doThrow(new ResourceNotFoundException("Task resource not found with ID=2"))
            .when(taskService)
            .deleteOne(2L);

        mockMvc.perform(delete("/task/1"))
            .andExpect(status().isNoContent());

        mockMvc.perform(delete("/task/2"))
            .andExpect(status().isNotFound());
    
        verify(taskService, times(2)).deleteOne(any(Long.class));
    }
}
