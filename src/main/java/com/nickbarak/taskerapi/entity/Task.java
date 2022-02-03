package com.nickbarak.taskerapi.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task implements Serializable {
    private int id;
    private String content;
    private Date date;
    private boolean isComplete;

    public Task(String content) {
        this.id = 1;
        this.content = content;
        this.date = new Date();
        this.isComplete = false;
    }
}
