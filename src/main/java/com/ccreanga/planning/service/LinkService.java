package com.ccreanga.planning.service;

import com.ccreanga.planning.domain.Link;
import com.ccreanga.planning.repository.LinkRepository;
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
 * Service Implementation for managing Link.
 */
@Service
@Transactional
public class LinkService {

    private final Logger log = LoggerFactory.getLogger(LinkService.class);
    
    @Inject
    private LinkRepository linkRepository;
    
    /**
     * Save a link.
     * @return the persisted entity
     */
    public Link save(Link link) {
        log.debug("Request to save Link : {}", link);
        Link result = linkRepository.save(link);
        return result;
    }

    /**
     *  get all the links.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Link> findAll(Pageable pageable) {
        log.debug("Request to get all Links");
        Page<Link> result = linkRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one link by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Link findOne(Long id) {
        log.debug("Request to get Link : {}", id);
        Link link = linkRepository.findOne(id);
        return link;
    }

    /**
     *  delete the  link by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Link : {}", id);
        linkRepository.delete(id);
    }
}
