package com.kamil.TaskManagement.repository;


import com.kamil.TaskManagement.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();


    public void addTasks(Task t) {
        tasks.add(t);
    }



    public List<Task> getTasks() {
        return tasks;
    }

    public TaskRepository() {
        tasks.add(new Task(1, "Set up Spring Boot", "IN_PROGRESS"));
        tasks.add(new Task(2, "Connect to Postgres", "TODO"));
    }
}
