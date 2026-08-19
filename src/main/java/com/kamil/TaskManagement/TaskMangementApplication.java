package com.kamil.TaskManagement;

import com.github.javafaker.Faker;
import com.kamil.TaskManagement.config.ApplicationConfiguration;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class TaskMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMangementApplication.class, args);
	}

	@Bean // testing the repo at startup
	public CommandLineRunner commandLineRunner(
			TaskRepository repository,
			TagRepository tagRepository,
			UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
		return args -> {

			Faker faker = new Faker();
			Random random = new Random();
			List<User> allUsers = userRepository.findAll();
			List<Tag> allTags = tagRepository.findAll();
			List<Project> allProjects = projectRepository.findAll();

			for (Project project : allProjects) {
				for(int i = 0; i < 50; i++) {
					var task = Task.builder()
							.id(null)
							.status(faker.options().option("TODO", "IN_PROGRESS", "IN_REVIEW", "COMPLETED"))
							.title(faker.hacker().verb() + " the " + faker.hacker().noun())
							.tags(Set.of(allTags.get(random.nextInt(allTags.size()))))
							.dueDate(LocalDateTime.now().plusDays(faker.number().numberBetween(1,30)))
							.project(project)
							.user(allUsers.get(random.nextInt(allUsers.size())))
							.build();
							taskRepository.save(task);
				}
			}





		};
	}

}
