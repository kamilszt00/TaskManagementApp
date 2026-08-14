package com.kamil.TaskManagement.model;

public class Task {
    private final String id;
    private final String status;
    private final String title;


    public Task() {
        this.title = "default";
        this.id = "000";
        this.status = "unknown";
    }

    public Task(String title, String id, String status) {
        this.title=title;
        this.id=id;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.join("|", title, id , status);
    }
}
