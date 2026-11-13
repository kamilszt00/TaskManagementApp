package com.kamil.TaskManagement.mapper;


import com.kamil.TaskManagement.DTO.CommentResponse;
import com.kamil.TaskManagement.DTO.CreateCommentRequest;
import com.kamil.TaskManagement.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toRequest(CreateCommentRequest request);

    @Mapping(target = "authorUsername", source = "author.username")
    @Mapping(target = "taskTitle", source = "task.title")
    CommentResponse toResponse(Comment comment);

}
