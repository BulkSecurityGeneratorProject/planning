package com.ccreanga.planning.service;

import com.ccreanga.planning.domain.Task;
import com.ccreanga.planning.repository.TaskRepository;
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
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);
    
    @Inject
    private TaskRepository taskRepository;
    
    /**
     * Save a task.
     * @return the persisted entity
     */
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        Task result = taskRepository.save(task);
        return result;
    }

    /**
     *  get all the tasks.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        Page<Task> result = taskRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one task by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Task findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        Task task = taskRepository.findOneWithEagerRelationships(id);
        return task;
    }

    /**
     *  delete the  task by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.delete(id);
    }
}
