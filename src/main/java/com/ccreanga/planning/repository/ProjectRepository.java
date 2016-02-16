package com.ccreanga.planning.repository;

import com.ccreanga.planning.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.responsible.login = ?#{principal.username}")
    List<Project> findByResponsibleIsCurrentUser();

    @Query("select distinct project from Project project left join fetch project.links left join fetch project.repositories")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.links left join fetch project.repositories where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);

}
