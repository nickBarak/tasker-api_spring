package com.nickbarak.taskerapi.controller;

import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.model.AuthenticationRequest;
import com.nickbarak.taskerapi.model.AuthenticationResponse;
import com.nickbarak.taskerapi.service.UserService;
import com.nickbarak.taskerapi.util.JwtUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            userService.loadUserByUsername(authenticationRequest.getUsername());
            return new ResponseEntity<String>("User exists with username=" + authenticationRequest.getUsername(), HttpStatus.CONFLICT);
        } catch (UsernameNotFoundException e) {
            User user = userService.saveOne(authenticationRequest);
            String jwt = jwtUtility.generateToken(user);
            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt, user), HttpStatus.CREATED);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception, BadCredentialsException {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<String>("Bad credentials for username=" + authenticationRequest.getUsername(), HttpStatus.UNAUTHORIZED);
        }

        User user = (User) userService.loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtUtility.generateToken(user);

        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt, user), HttpStatus.OK);
    }
}
