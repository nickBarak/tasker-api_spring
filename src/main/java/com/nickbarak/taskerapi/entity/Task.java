package com.nickbarak.taskerapi.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// @AllArgsConstructor
// @NoArgsConstructor
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private Date date;
    private boolean isComplete;

    public Task() {}

    public Task(String content) {
        super();
        this.content = content;
        this.date = new Date();
        this.isComplete = false;
    }

    public Task(long id, String content, Date date, boolean isComplete) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.isComplete = isComplete;
    }

    public Long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
