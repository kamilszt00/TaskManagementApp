package com.kamil.TaskManagement.mapper;



import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "userTasks", source = "tasks")
    UserResponse toResponse(User user);

    User toRequest(CreateUserRequest request);

    default String taskToName(Task task) {
        return task.getTitle();
    }


}
