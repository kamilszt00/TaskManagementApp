package com.kamil.TaskManagement.service;

import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.exception.InvalidTaskStateException;
import com.kamil.TaskManagement.mapper.TaskMapper;
import com.kamil.TaskManagement.model.*;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Spy
    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TaskService taskService;



    @Test
    public void shouldCreateTaskSuccessfully() {
        CreateTaskRequest request = CreateTaskRequest.builder()
                .title("The title")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(33))
                .projectID(1)
                .assigneeID(1)
                .tagsID(new HashSet<>(Arrays.asList(1,2,3)))
                .build();
        //Tags mock
        Tag tag1 = new Tag(); tag1.setId(1);
        Tag tag2 = new Tag(); tag2.setId(2);
        Tag tag3 = new Tag(); tag3.setId(3);
        //Project Mock
        Project proj = new Project(); proj.setId(1);
        User user = new User(); user.setId(1);
        Mockito.when(tagRepository.findAllById(request.getTagsID())).thenReturn(Arrays.asList(tag1,tag2,tag3));
        Mockito.when(projectRepository.getReferenceById(request.getProjectID())).thenReturn(proj);
        Mockito.when(userRepository.getReferenceById(request.getAssigneeID())).thenReturn(user);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ResponseEntity<TaskResponse> taskResponse = taskService.createTask(request);

        assertThat(request)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringCollectionOrder()
                .isEqualTo(taskResponse.getBody());
    }

    @Test
    public void shouldCompleteTaskSuccessfully() {
        Task task = createMockTask(TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ResponseEntity<TaskResponse> taskResponse = taskService.completeTask(task.getId());

        assertThat(taskResponse.getBody().getStatus()).isEqualTo(TaskStatus.COMPLETED.name());
    }
    @Test
    public void shouldThrowWhenCompletingTodoTask() {
        Task task = createMockTask(TaskStatus.TODO);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> taskService.completeTask(task.getId()))
                .isInstanceOf(InvalidTaskStateException.class);
    }

    @Test
    public void shouldThrowWhenCompletingCompletedTask() {
        Task task = createMockTask(TaskStatus.COMPLETED);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> taskService.completeTask(task.getId()))
                .isInstanceOf(InvalidTaskStateException.class);

    }

    @Test
    public void shouldStartTaskSuccessfully() {
        Task task = createMockTask(TaskStatus.TODO);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ResponseEntity<TaskResponse> taskResponse = taskService.startTask(task.getId());

        assertThat(taskResponse.getBody().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS.name());
    }
    @Test
    public void shouldThrowWhenStartingInProgressTask() {
        Task task = createMockTask(TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> taskService.startTask(task.getId()))
                .isInstanceOf(InvalidTaskStateException.class);
    }
    @Test
    public void shouldThrowWhenStaringCompletedTask() {
        Task task = createMockTask(TaskStatus.COMPLETED);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        assertThatThrownBy(() -> taskService.startTask(task.getId()))
                .isInstanceOf(InvalidTaskStateException.class);
    }


    private static Task createMockTask(TaskStatus taskStatus) {
        Tag tag1 = new Tag(); tag1.setId(1);
        Tag tag2 = new Tag(); tag2.setId(2);
        Tag tag3 = new Tag(); tag3.setId(3);
        Project project = new Project(); project.setId(1);
        User user = new User(); user.setId(1);
        return Task.builder()
                .id(1)
                .status(taskStatus)
                .title("The title")
                .tags(new HashSet<>(Arrays.asList(tag1,tag2,tag3)))
                .dueDate(LocalDateTime.now().plusDays(33))
                .project(project)
                .user(user)
                .build();
    }

}