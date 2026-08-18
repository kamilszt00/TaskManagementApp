package com.kamil.TaskManagement;

import com.kamil.TaskManagement.config.ApplicationConfiguration;
import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TaskMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMangementApplication.class, args);
	}

	@Bean // testing the repo at startup
	public CommandLineRunner commandLineRunner(
			TaskRepository repository,
			TagRepository tagRepository
	) {
		return args -> {
			var task = Task.builder().id(null).status("nie").title("starting").dueDate(LocalDateTime.now()).build();
			repository.save(task);
            List<Tag> tags = new ArrayList<>();
			tags.add(Tag.builder().id(null).tag_name("Urgent").build());
			tags.add(Tag.builder().id(null).tag_name("Bug").build());
			tags.add(Tag.builder().id(null).tag_name("Enhancement").build());
            tagRepository.saveAll(tags);
		};
	}

}
