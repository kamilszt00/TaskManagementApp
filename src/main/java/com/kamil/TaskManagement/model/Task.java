package com.kamil.TaskManagement.model;

public class Task {
    private final int id;
    private final String status;
    private final String title;


    public Task() {
        this.title = "default";
        this.id = 0;
        this.status = "unknown";
    }

    public Task(int id, String title, String status) {
        this.title=title;
        this.id=id;
        this.status=status;
    }

    public int getId() {
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
        return id + " | " + String.join("|", title , status);
    }
}
