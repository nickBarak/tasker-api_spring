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

    public boolean saveOne(Task task) throws Exception {
        taskRepository.save(task);
        return true;
    }

    public List<Task> getAll() throws Exception {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        return tasks;
    }

    public boolean deleteOne(Long id) throws Exception, ResourceNotFoundException {
            if (!taskRepository.existsById(id)) {
                throw new ResourceNotFoundException("Task resource not found with ID=" + id);
            }
            taskRepository.deleteById(id);
            return true;
    }
}