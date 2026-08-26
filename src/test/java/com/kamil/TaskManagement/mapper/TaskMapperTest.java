package com.kamil.TaskManagement.mapper;

import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


class TaskMapperTest {
    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);



    @Test
    public void shouldMapTaskDtoToTask() {
        CreateTaskRequest dto = CreateTaskRequest.builder()
                .title("The title")
                .status("TODO")
                .DueDate(LocalDateTime.now().plusDays(33))
                .projectID(1)
                .assigneeID(1)
                .tagsID(new HashSet<>(Arrays.asList(1,2,3)))
                .build();

        Task task = mapper.toRequest(dto);
        assertEquals(dto.getTitle(),task.getTitle());
        assertEquals(dto.getStatus(),task.getStatus());
        assertNotNull(task.getDueDate());
        assertEquals(dto.getDueDate(),task.getDueDate());
        assertNull(task.getProject());
        assertNull(task.getUser());
        assertTrue(task.getTags() == null || task.getTags().isEmpty());
    }

}