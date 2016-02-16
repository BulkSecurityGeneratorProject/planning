package com.ccreanga.planning.service;

import com.ccreanga.planning.domain.SecurityReview;
import com.ccreanga.planning.repository.SecurityReviewRepository;
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
 * Service Implementation for managing SecurityReview.
 */
@Service
@Transactional
public class SecurityReviewService {

    private final Logger log = LoggerFactory.getLogger(SecurityReviewService.class);
    
    @Inject
    private SecurityReviewRepository securityReviewRepository;
    
    /**
     * Save a securityReview.
     * @return the persisted entity
     */
    public SecurityReview save(SecurityReview securityReview) {
        log.debug("Request to save SecurityReview : {}", securityReview);
        SecurityReview result = securityReviewRepository.save(securityReview);
        return result;
    }

    /**
     *  get all the securityReviews.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SecurityReview> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityReviews");
        Page<SecurityReview> result = securityReviewRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one securityReview by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SecurityReview findOne(Long id) {
        log.debug("Request to get SecurityReview : {}", id);
        SecurityReview securityReview = securityReviewRepository.findOneWithEagerRelationships(id);
        return securityReview;
    }

    /**
     *  delete the  securityReview by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecurityReview : {}", id);
        securityReviewRepository.delete(id);
    }
}
