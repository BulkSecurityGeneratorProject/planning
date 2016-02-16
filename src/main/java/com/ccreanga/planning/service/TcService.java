package com.ccreanga.planning.service;

import com.ccreanga.planning.domain.Tc;
import com.ccreanga.planning.repository.TcRepository;
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
 * Service Implementation for managing Tc.
 */
@Service
@Transactional
public class TcService {

    private final Logger log = LoggerFactory.getLogger(TcService.class);
    
    @Inject
    private TcRepository tcRepository;
    
    /**
     * Save a tc.
     * @return the persisted entity
     */
    public Tc save(Tc tc) {
        log.debug("Request to save Tc : {}", tc);
        Tc result = tcRepository.save(tc);
        return result;
    }

    /**
     *  get all the tcs.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tc> findAll(Pageable pageable) {
        log.debug("Request to get all Tcs");
        Page<Tc> result = tcRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one tc by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tc findOne(Long id) {
        log.debug("Request to get Tc : {}", id);
        Tc tc = tcRepository.findOneWithEagerRelationships(id);
        return tc;
    }

    /**
     *  delete the  tc by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tc : {}", id);
        tcRepository.delete(id);
    }
}
