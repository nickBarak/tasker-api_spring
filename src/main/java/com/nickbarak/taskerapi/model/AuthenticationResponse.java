package com.nickbarak.taskerapi.model;

import com.nickbarak.taskerapi.entity.User;

public class AuthenticationResponse {
    private String jwt;
    private UserResponse user;

    public AuthenticationResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = new UserResponse(user);
    }

    public String getJwt() {
        return jwt;
    }

    public UserResponse getUser() {
        return this.user;
    }
}
