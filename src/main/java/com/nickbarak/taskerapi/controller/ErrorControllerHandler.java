package com.nickbarak.taskerapi.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControllerHandler implements ErrorController {
    
    @GetMapping("/error")
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        return "<h1>Error</h1><br></br><div>Status: " + statusCode + "</div><br></br><div>Exception Type: " + exceptionType + "</div><br></br><div>Request URI: " + requestUri + "</div><br></br><div>Message: " + message + "</div>";
    }
}
