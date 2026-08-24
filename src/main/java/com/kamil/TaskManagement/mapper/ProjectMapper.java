package com.kamil.TaskManagement.mapper;


import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "tasksName", source = "tasks")
    ProjectResponse toResponse(Project project);

    Project toRequest(CreateProjectRequest request);

    default String taskToName(Task task) {
        return task.getTitle();
    }

}
