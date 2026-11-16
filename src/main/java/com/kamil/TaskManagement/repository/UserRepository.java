package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByIdBetween(Integer idAfter, Integer idBefore);


    Optional<User> findByUsername(String username);

    boolean existsByRole(UserRole role);


    List<User> findByRoleOrderByUsernameAsc(UserRole role);

}
