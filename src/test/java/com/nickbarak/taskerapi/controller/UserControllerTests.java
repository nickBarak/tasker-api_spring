package com.nickbarak.taskerapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.model.UserRequest;
import com.nickbarak.taskerapi.service.UserService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .build();
    }

    @Test
    public void doGet() throws Exception {
        List<User> users = new LinkedList<>();
        users.add(new User(1L, "user1", "pass", "USER"));
        users.add(new User(2L, "user2", "mypass", "ADMIN"));
        users.add(new User(3L, "user3", "pass", "USER"));
        when(userService.getAll())
            .thenReturn(users);

        mockMvc.perform(get("/user"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)))
            .andExpect(jsonPath("$[1].username").value("user2"))
            .andExpect(jsonPath("$[1].role").value("ADMIN"));

        verify(userService).getAll();
    }

    @Test
    public void doGetOne() throws Exception {
        User user = new User(1L, "user1", "pass", "USER");
        doThrow(new ResourceNotFoundException("User resource not found with ID=2"))
            .when(userService)
            .getOne(2L);

        when(userService.getOne(1L))
            .thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/2"))
            .andExpect(status().isNotFound());

        mockMvc.perform(get("/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("user1"));

        verify(userService, times(2)).getOne(anyLong());
    }

    @Test
    public void doPut() throws Exception {
        User user = new User(1L, "user1", "pass", "USER");
        UserRequest userRequest = new UserRequest(1L, "user1", "pass", "USER");
        String json = new ObjectMapper().writeValueAsString(userRequest);
        when(userService.updateOne(any(UserRequest.class)))
            .thenReturn(user);

        mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());

        mockMvc.perform(put("/user/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("23"))
        .andExpect(status().isBadRequest());

        verify(userService).updateOne(any(UserRequest.class));
    }

    @Test
    public void doDelete() throws Exception {
        doNothing()
            .when(userService)
            .deleteOne(1L);

        doThrow(new ResourceNotFoundException("Task resource not found with ID=2"))
            .when(userService)
            .deleteOne(2L);

        mockMvc.perform(delete("/user/1"))
            .andExpect(status().isNoContent());

        mockMvc.perform(delete("/user/2"))
            .andExpect(status().isNotFound());
    
        verify(userService, Mockito.times(2)).deleteOne(anyLong());
    }
}
