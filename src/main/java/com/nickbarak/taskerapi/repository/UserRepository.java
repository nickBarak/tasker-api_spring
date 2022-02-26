package com.nickbarak.taskerapi.repository;

import com.nickbarak.taskerapi.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public boolean existsByUsername(String username);
    
    public User findByUsername(String username);
}
