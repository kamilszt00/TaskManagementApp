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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Unit Tests")
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

    private CreateTaskRequest request;
    private Task task;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Project project;
    private User user;

    @BeforeEach
    void setUp() {
        this.tag1 = new Tag(); tag1.setId(1);
        this.tag2 = new Tag(); tag2.setId(2);
        this.tag3 = new Tag(); tag3.setId(3);
        this.project = new Project(); project.setId(1);
        this.user = new User(); user.setId(1);
        this.task = Task.builder()
                .id(1)
                .status(null)
                .title("The title")
                .tags(new HashSet<>(Arrays.asList(tag1,tag2,tag3)))
                .dueDate(LocalDateTime.now().plusDays(33))
                .project(project)
                .user(user)
                .build();
        this.request = CreateTaskRequest.builder()
                .title("The title")
                .status("TODO")
                .dueDate(LocalDateTime.now().plusDays(33))
                .projectID(1)
                .assigneeID(1)
                .tagsID(new HashSet<>(Arrays.asList(1,2,3)))
                .build();
    }

    @Nested
    @DisplayName("Create Task tests")
    class CreateTaskTests {
        @Test
        @DisplayName("Should create Task successfully when valid request exists")
        public void shouldCreateTaskSuccessfully() {
            Mockito.when(tagRepository.findAllById(request.getTagsID())).thenReturn(Arrays.asList(tag1,tag2,tag3));
            Mockito.when(projectRepository.getReferenceById(request.getProjectID())).thenReturn(project);
            Mockito.when(userRepository.getReferenceById(request.getAssigneeID())).thenReturn(user);
            Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
            ResponseEntity<TaskResponse> taskResponse = taskService.createTask(request);
            assertThat(request)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .ignoringCollectionOrder()
                    .isEqualTo(taskResponse.getBody());
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.times(1)).save(Mockito.any(Task.class));

        }
    }
    @Nested
    @DisplayName("Delete Task tests")
    class DeleteTaskTests {
        @Test
        @DisplayName("Should delete Task successfully")
        public void shouldDeleteTaskSuccessfully() {
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            taskService.deleteTask(task.getId());
            Mockito.verify(taskRepository, Mockito.times(1)).delete(task);
        }
    }

    @Nested
    @DisplayName("Start Task Tests")
    class StartTaskTests {
        @Test
        @DisplayName("Should start task if taskStatus is TODO")
        public void shouldStartTaskSuccessfully() {
            task.setStatus(TaskStatus.TODO);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
            ResponseEntity<TaskResponse> taskResponse = taskService.startTask(task.getId());

            assertThat(taskResponse.getBody().getStatus()).isEqualTo(TaskStatus.IN_PROGRESS.name());

        }
        @Test
        @DisplayName("Should throw InvalidTaskStateException if starting task with IN_PROGRESS status")
        public void shouldThrowWhenStartingInProgressTask() {
            task.setStatus(TaskStatus.IN_PROGRESS);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
            assertThatThrownBy(() -> taskService.startTask(task.getId()))
                    .isInstanceOf(InvalidTaskStateException.class);
        }
        @Test
        @DisplayName("Should throw InvalidTaskStateException if starting task with COMPLETED status")
        public void shouldThrowWhenStaringCompletedTask() {
            task.setStatus(TaskStatus.COMPLETED);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
            assertThatThrownBy(() -> taskService.startTask(task.getId()))
                    .isInstanceOf(InvalidTaskStateException.class);
        }
    }


    @Nested
    @DisplayName("Complete Task tests")
    class CompleteTaskTests {
        @Test
        @DisplayName("Should complete task if taskStatus is IN_PROGRESS")
        public void shouldCompleteTaskSuccessfully() {
            task.setStatus(TaskStatus.IN_PROGRESS);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            Mockito.when(taskRepository.save(task)).thenAnswer(invocation -> invocation.getArgument(0));
            ResponseEntity<TaskResponse> taskResponse = taskService.completeTask(task.getId());

            assert taskResponse.getBody() != null;
            assertThat(taskResponse.getBody().getStatus()).isEqualTo(TaskStatus.COMPLETED.name());
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.times(1)).save(task);

        }
        @Test
        @DisplayName("Should throw InvalidTaskStateException if completing task with TODO status")
        public void shouldThrowWhenCompletingTodoTask() {
            task.setStatus(TaskStatus.TODO);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO);
            assertThatThrownBy(() -> taskService.completeTask(task.getId()))
                    .isInstanceOf(InvalidTaskStateException.class);
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.times(0)).save(task);

        }

        @Test
        @DisplayName("Should throw InvalidTaskStateException if completing task with COMPLETED status")
        public void shouldThrowWhenCompletingCompletedTask() {
            task.setStatus(TaskStatus.COMPLETED);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            assertThat(task.getStatus()).isEqualTo(TaskStatus.COMPLETED);
            assertThatThrownBy(() -> taskService.completeTask(task.getId()))
                    .isInstanceOf(InvalidTaskStateException.class);
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.times(0)).save(task);
        }
    }
    @Nested
    @DisplayName("Return overdue tasks test")
    class GetOverDueTasksTests {
        @Test
        @DisplayName("Should return overDue tasks successfully")
        public void shouldGetOverDueTasksSuccessfully() {
            List<Task> overDueTasks = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Tag tag1 = new Tag(); tag1.setId(1);
                Tag tag2 = new Tag(); tag2.setId(2);
                Tag tag3 = new Tag(); tag3.setId(3);
                Project project = new Project(); project.setId(1);
                User user = new User(); user.setId(1);
                Task task = Task.builder()
                        .id(1)
                        .status(null)
                        .title("The title")
                        .tags(new HashSet<>(Arrays.asList(tag1,tag2,tag3)))
                        .dueDate(LocalDateTime.now().plusDays(33))
                        .project(project)
                        .user(user)
                        .build();
                overDueTasks.add(task);
            }
            Mockito.when(taskRepository.findOverdueTasks(Mockito.any(LocalDateTime.class), Mockito.eq(Arrays.asList(TaskStatus.TODO, TaskStatus.IN_PROGRESS))))
                    .thenReturn(overDueTasks);

            ResponseEntity<List<TaskResponse>> response = taskService.getOverdueTask();

            assertThat(response.getBody()).hasSize(3);
        }
    }

    @Nested
    @DisplayName("Reassign Task tests")
    class reassignTaskTests {
        @Test
        @DisplayName("Should reassign task test if Different user and Task NOT completed")
        public void shouldReassignTaskSuccessfully() {
            User userToReassign = new User(); userToReassign.setId(2);
            task.setStatus(TaskStatus.TODO);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            Mockito.when(userRepository.getReferenceById(userToReassign.getId())).thenReturn(userToReassign);
            Mockito.when(taskRepository.save(task)).thenAnswer(invocation -> invocation.getArgument(0));
            ResponseEntity<TaskResponse> taskResponse = taskService.reassignTask(task.getId(),userToReassign.getId());
            assert taskResponse.getBody() != null;
            assertThat(taskResponse.getBody().getAssigneeID()).isEqualTo(userToReassign.getId());
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.times(1)).save(task);
        }
        @Test
        @DisplayName("Should throw InvalidTaskStateException if trying to reassign to the same user")
        public void shouldThrowWhenReassigningSameUser() {
            task.setStatus(TaskStatus.TODO);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            Mockito.when(userRepository.getReferenceById(user.getId())).thenReturn(user);
            assertThatThrownBy(() -> taskService.reassignTask(task.getId(),user.getId())).isInstanceOf(InvalidTaskStateException.class);
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.never()).save(task);
        }
        @Test
        @DisplayName("Should throw InvalidTaskStateException if trying to reassign completed task")
        public void shouldThrowWhenReassignCompletedTask() {
            User userToReassign = new User(); userToReassign.setId(2);
            task.setStatus(TaskStatus.COMPLETED);
            Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            Mockito.when(userRepository.getReferenceById(userToReassign.getId())).thenReturn(userToReassign);
            assertThatThrownBy(() -> taskService.reassignTask(task.getId(),userToReassign.getId())).isInstanceOf(InvalidTaskStateException.class);
            Mockito.verify(TaskServiceTest.this.taskRepository,Mockito.never()).save(task);

        }


    }






}