package com.kamil.TaskManagement;

import com.kamil.TaskManagement.config.ApplicationConfiguration;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TaskMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMangementApplication.class, args);



	}

}
