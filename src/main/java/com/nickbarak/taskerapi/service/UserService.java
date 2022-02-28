package com.nickbarak.taskerapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.model.AuthenticationRequest;
import com.nickbarak.taskerapi.model.UserRequest;
import com.nickbarak.taskerapi.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException("User resource not found with username=" + username);
        } else return userRepository.findByUsername(username);
    }

    public User saveOne(AuthenticationRequest authenticationRequest) throws Exception {
        String hashedPassword = BCrypt.hashpw(authenticationRequest.getPassword(), BCrypt.gensalt(12));
        User user = new User(authenticationRequest.getUsername(), hashedPassword);
        return userRepository.save(user);
    }

    public User updateOne(UserRequest userRequest) throws Exception, UsernameNotFoundException {
        if (!userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UsernameNotFoundException("User resource not found with username=" + userRequest.getUsername());
        } else {
            User user = (User) loadUserByUsername(userRequest.getUsername());
            String hashedPassword = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt(12));
            user.setPassword(hashedPassword);
            return userRepository.save(user);
        }
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> getOne(long id) throws Exception, ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User resource not found with id=" + id);
        } else return userRepository.findById(id);
    }

    public void deleteOne(long id) throws Exception, ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User resource not found with id=" + id);
        } else userRepository.deleteById(id);
    }
}
