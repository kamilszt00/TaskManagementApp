package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByIdBetween(Integer idAfter, Integer idBefore);
    Optional<User> findByUsername(String username);
}
