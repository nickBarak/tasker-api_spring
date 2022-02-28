package com.nickbarak.taskerapi.model;

import java.util.Date;

public class TaskRequest {
    private long id;
    private String content;
    private Date date;
    private boolean isComplete;
    private String author;

    public TaskRequest() {}

    public TaskRequest(long id, String content, Date date, boolean isComplete, String author) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.isComplete = isComplete;
        this.author = author;
    }

    public long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    public String getAuthor() {
        return this.author;
    }
}
