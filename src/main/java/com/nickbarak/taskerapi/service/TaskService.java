package com.nickbarak.taskerapi.service;

import java.util.List;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public boolean saveOne(Task task) {
        return taskRepository.saveOne(task);
    }

    public List<Task> getAll() {
        return taskRepository.getAll();
    }

    public boolean deleteOne(int id) {
        return taskRepository.deleteOne(id);
    }
}
