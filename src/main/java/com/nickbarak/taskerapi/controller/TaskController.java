package com.nickbarak.taskerapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    
    @PostMapping
    public ResponseEntity<HttpStatus> doPost(@RequestBody Task task, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean savedSuccessfully = taskService.saveOne(task);
        return new ResponseEntity<HttpStatus>(savedSuccessfully ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Task>> doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Task> tasks = taskService.getAll();
        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> doPut(@RequestBody Task task, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean savedSuccessfully = taskService.saveOne(task);
        return new ResponseEntity<HttpStatus>(savedSuccessfully ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> doDelete(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception, ResourceNotFoundException {
        boolean deletedSuccessfully = taskService.deleteOne(id);
        return new ResponseEntity<HttpStatus>(deletedSuccessfully ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
