package com.kamil.TaskManagement.mapper;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "projectID", source = "project.id")
    @Mapping(target = "assigneeID", source = "user.id")
    @Mapping(target = "tagsID", source = "tags")
    @Mapping(target="dueDate", source = "dueDate")
    TaskResponse toResponse(Task task);


    Task toRequest(CreateTaskRequest request);

    default Integer tagToId(Tag tag) {
        return tag.getId();
    }
}
