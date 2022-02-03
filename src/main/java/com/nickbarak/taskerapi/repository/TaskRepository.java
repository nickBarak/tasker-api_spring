package com.nickbarak.taskerapi.repository;

import java.util.ArrayList;
import java.util.List;

import com.nickbarak.taskerapi.entity.Task;

import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    public boolean saveOne(Task task) {
        // save to db...
        return true;
    }

    public List<Task> getAll() {
        // fetch from db...
        return new ArrayList<Task>();
    }

    public boolean deleteOne(int id) {
        // delete from db...
        return true;
    }
}
