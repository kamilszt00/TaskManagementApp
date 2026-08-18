package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
