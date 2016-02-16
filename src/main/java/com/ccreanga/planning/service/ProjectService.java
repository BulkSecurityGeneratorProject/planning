package com.ccreanga.planning.service;

import com.ccreanga.planning.domain.Project;
import com.ccreanga.planning.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);
    
    @Inject
    private ProjectRepository projectRepository;
    
    /**
     * Save a project.
     * @return the persisted entity
     */
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        Project result = projectRepository.save(project);
        return result;
    }

    /**
     *  get all the projects.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        Page<Project> result = projectRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one project by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Project findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        Project project = projectRepository.findOneWithEagerRelationships(id);
        return project;
    }

    /**
     *  delete the  project by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
}
