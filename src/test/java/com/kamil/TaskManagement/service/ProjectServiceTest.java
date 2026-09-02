package com.kamil.TaskManagement.service;

import com.github.javafaker.Faker;
import com.kamil.TaskManagement.DTO.ProjectResponse;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.exception.ArgumentsEqualException;
import com.kamil.TaskManagement.mapper.ProjectMapper;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.TaskStatus;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectService Unit Tests")
class ProjectServiceTest {
    private static final Faker faker = new Faker();
    @Spy
    private final ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {

    }

    @Nested
    @DisplayName("Delete Project Tests")
    class DeleteProjectTests {
        @Test
        @DisplayName("Should delete project successfully when valid ids supplied")
        public void shouldDeleteProjectSuccessfully() {
            Project projectToDelete = createMockProject(1);
            Project projectToReassign = createMockProject(2);
            Integer projectToDeleteId = projectToDelete.getId();
            Integer projectToReassignId = projectToReassign.getId();
            List<Task> incompletedTasks = mockTasksWithStatus(TaskStatus.IN_PROGRESS,projectToDeleteId);
            List<Task> completedTasks = mockTasksWithStatus(TaskStatus.COMPLETED,projectToDeleteId);


            Mockito.when(projectRepository.findById(projectToDeleteId)).thenReturn(Optional.of(projectToDelete));
            Mockito.when(projectRepository.findById(projectToReassignId)).thenReturn(Optional.of(projectToReassign));
            Mockito.when(taskRepository.findIncompletedTasks(projectToDeleteId))
                    .thenReturn(incompletedTasks);
            Mockito.when(taskRepository.findAllByProject(projectToDelete))
                    .thenReturn(completedTasks);
            ResponseEntity<ProjectResponse> projectResponse = projectService.deleteProject(projectToDeleteId,projectToReassignId);
            Mockito.verify(taskRepository,Mockito.times(1)).deleteAll(Mockito.anyList());
            Mockito.verify(projectRepository,Mockito.times(1)).delete(Mockito.any(Project.class));
            Mockito.verify(projectRepository).findById(projectToDeleteId);
            Mockito.verify(projectRepository).findById(projectToReassignId);
            incompletedTasks.forEach(task ->
                    assertThat(task.getProject().getId()).isEqualTo(projectToReassignId));
                    assertThat(projectResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
        @Test
        @DisplayName("Should throw ArgumentsEqualException if supplied parameters are the same")
        public void shouldThrowWhenProjectIdsEqual() {
            Mockito.verifyNoInteractions(taskRepository,projectRepository);
            assertThatThrownBy(() -> projectService.deleteProject(1,1))
                    .isInstanceOf(ArgumentsEqualException.class);
        }

    }


    private static Project createMockProject(Integer id) {
        return Project.builder()
                .id(id)
                .name(faker.company().name())
                .description(faker.company().profession())
                .createdAt(LocalDateTime.MIN)
                .tasks(null)
                .build();
    }
    private static List<Task> mockTasksWithStatus(TaskStatus status, Integer id) {
        Task task1 = new Task(); task1.setStatus(status); task1.setId(id);
        Task task2 = new Task(); task2.setStatus(status); task2.setId(id);
        Task task3 = new Task(); task3.setStatus(status); task3.setId(id);
        return Arrays.asList(task1,task2,task3);
    }



}