package com.nickbarak.taskerapi.service;

import java.util.List;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public boolean saveOne(Task task) {
        System.out.println(task.getContent());
        taskRepository.save(task);
        return true;
    }

    public List<Task> getAll() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return tasks;
    }

    public boolean deleteOne(Long id) throws ResourceNotFoundException {
        taskRepository.deleteById(id);
        return true;
    }
}
