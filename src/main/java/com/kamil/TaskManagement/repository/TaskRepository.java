package com.kamil.TaskManagement.repository;


import com.kamil.TaskManagement.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();



    public void addToList(Task T) {
        tasks.add(T);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
