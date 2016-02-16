package com.ccreanga.planning.repository;

import com.ccreanga.planning.domain.Task;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select task from Task task where task.securityEngineer.login = ?#{principal.username}")
    List<Task> findBySecurityEngineerIsCurrentUser();

    @Query("select distinct task from Task task left join fetch task.notess left join fetch task.linkss")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.notess left join fetch task.linkss where task.id =:id")
    Task findOneWithEagerRelationships(@Param("id") Long id);

}
