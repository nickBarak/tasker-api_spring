package com.nickbarak.taskerapi.controller;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = new ArrayList<>();
        User user2 = new User(2L, "user 2", "pass 2");
        User user3 = new User(3L, "user 3", "pass 3");
        users.add(user2);
        users.add(user3);
        return ResponseEntity.ok().body(users);
    }
}
