package com.kamil.TaskManagement;

import com.github.javafaker.Faker;
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
//			for (int i = 0; i < 50; i++) {
//				Faker faker = new Faker();
//				var task = Task.builder()
//						.id(null)
//						.status(faker.options().option("TODO", "IN_PROGRESS", "IN_REVIEW", "COMPLETED"))
//						.title(faker.company().bs())
//						.dueDate(LocalDateTime.now().plusDays(faker.number().numberBetween(1,30)))
//						.build();
//				repository.save(task);
//
//			}
//
//			List<Tag> tags = new ArrayList<>();
//			tags.add(Tag.builder().id(null).tag_name("URGENT").build());
//			tags.add(Tag.builder().id(null).tag_name("BUG").build());
//			tags.add(Tag.builder().id(null).tag_name("ENHANCEMENT").build());
//			tagRepository.saveAll(tags);
		};
	}

}
