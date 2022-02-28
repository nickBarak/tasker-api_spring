package com.nickbarak.taskerapi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    @ManyToOne()
    @JoinColumn(name="author", referencedColumnName = "username")
    private User author;

    public Task() {}

    public Task(String content, User author) {
        this.content = content;
        this.date = new Date();
        this.isComplete = false;
        this.author = author;
    }

    public Task(String content, Date date, boolean isComplete, User author) {
        this.content = content;
        this.date = date;
        this.isComplete = isComplete;
        this.author = author;
    }

    public Task(long id, String content, Date date, boolean isComplete, User author) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.isComplete = isComplete;
        this.author = author;
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

    public String getAuthor() {
        return this.author.getUsername();
    }
}
