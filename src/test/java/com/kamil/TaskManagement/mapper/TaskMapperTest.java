package com.kamil.TaskManagement.mapper;

import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.model.Task;
import org.mapstruct.factory.Mappers;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class TaskMapperTest {
    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);
    @Test
    public void shouldMapTaskDtoToTask() {
        CreateTaskRequest dto = CreateTaskRequest.builder()

                .title("The title")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(33))
                .build();

        Task task = mapper.toRequest(dto);
        assertThat(task)
                .usingRecursiveComparison()
                .ignoringFields("project","user","tags","id")
                .isEqualTo(dto);
    }

}