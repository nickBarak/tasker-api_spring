package com.nickbarak.taskerapi.model;

import java.util.Optional;

import com.nickbarak.taskerapi.entity.User;

public class UserResponse {
    private long id;
    private String username;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public UserResponse(Optional<User> user) {
        if (user.isPresent()) {
            this.id = user.get().getId();
            this.username = user.get().getUsername();
            this.role = user.get().getRole();
        } else {
            this.id = -1L;
            this.username = "Invalid User";
            this.role = "NONE";
        }
    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }
}
