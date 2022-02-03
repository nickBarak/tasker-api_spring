package com.nickbarak.taskerapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    
    @PostMapping
    public ResponseEntity<HttpStatus> doPost(String content, HttpServletRequest req, HttpServletResponse res) {
        Task task = new Task(content);
        boolean savedSuccessfully = taskService.saveOne(task);
        return new ResponseEntity<HttpStatus>(savedSuccessfully ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Task>> doGet(HttpServletRequest req, HttpServletResponse res) {
        List<Task> tasks = taskService.getAll();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> doPut(Task task, HttpServletRequest req, HttpServletResponse res) {
        boolean savedSuccessfully = taskService.saveOne(task);
        return new ResponseEntity<HttpStatus>(savedSuccessfully ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> doDelete(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
        boolean deletedSuccessfully = taskService.deleteOne(id);
        return new ResponseEntity<HttpStatus>(deletedSuccessfully ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
