package com.nickbarak.taskerapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.model.TaskRequest;
import com.nickbarak.taskerapi.service.TaskService;
import com.nickbarak.taskerapi.service.UserService;
import com.nickbarak.taskerapi.util.JwtUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;
    
    @PostMapping()
    public ResponseEntity<Task> doPost(@RequestBody String content, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = jwtUtility.extractUsernameFromHeader(request.getHeader("Authorization"));
        User user = (User) userService.loadUserByUsername(username);
        Task task = new Task(content, user);
        Task savedTask = taskService.saveOne(task);
        return new ResponseEntity<Task>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = jwtUtility.extractUsernameFromHeader(request.getHeader("Authorization"));
        List<Task> tasks = taskService.getAllByUser(username);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> doGetOne(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<Task> task = taskService.getOne(id);
        return new ResponseEntity<Optional<Task>>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> doPut(@RequestBody TaskRequest taskRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) userService.loadUserByUsername(taskRequest.getAuthor());
        Task task = new Task(taskRequest.getId(), taskRequest.getContent(), taskRequest.getDate(), taskRequest.getIsComplete(), user);
        Task savedTask = taskService.saveOne(task);
        return new ResponseEntity<Task>(savedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> doDelete(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception, ResourceNotFoundException {
        boolean deletedSuccessfully = taskService.deleteOne(id);
        return new ResponseEntity<HttpStatus>(deletedSuccessfully ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}
