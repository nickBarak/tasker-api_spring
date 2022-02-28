package com.nickbarak.taskerapi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {
    
    @GetMapping
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "bad ending";
    }
}
