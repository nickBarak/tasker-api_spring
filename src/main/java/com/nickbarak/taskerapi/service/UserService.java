package com.nickbarak.taskerapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.entity.User;

@Service
public class UserService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(1L, username, "pass"/*, new ArrayList<Task>()*/);
    }
}
