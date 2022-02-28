package com.nickbarak.taskerapi.repository;

import java.util.List;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.entity.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    public List<Task> findAllByAuthor(User author);
}
