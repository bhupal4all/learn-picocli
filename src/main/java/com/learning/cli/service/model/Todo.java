package com.learning.cli.service.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Serializable {
    Long id;
    String message;
    Status status = Status.CREATED;
    Date createdOn = new Date();
    Date modifiedOn = new Date();

    public Todo(String message) {
        this.message = message;
    }

    public Todo(String message, Date createdOn) {
        this.message = message;
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MMM/YY HH:MM");
        return "Todo{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", createdOn=" + simpleDateFormat.format(createdOn) +
                ", modifiedOn=" + simpleDateFormat.format(modifiedOn) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
