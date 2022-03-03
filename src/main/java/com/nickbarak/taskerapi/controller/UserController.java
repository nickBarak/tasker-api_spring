package com.nickbarak.taskerapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.model.UserRequest;
import com.nickbarak.taskerapi.model.UserResponse;
import com.nickbarak.taskerapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserResponse>> doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<User> users = userService.getAll();
        return ResponseEntity.ok().body(users.stream().map(UserResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> doGetOne(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Optional<User> user = userService.getOne(id);
        return new ResponseEntity<UserResponse>(new UserResponse(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> doPut(@RequestBody UserRequest userRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(userRequest instanceof UserRequest)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User updatedUser = userService.updateOne(userRequest);
        return new ResponseEntity<UserResponse>(new UserResponse(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> doDelete(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        userService.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
