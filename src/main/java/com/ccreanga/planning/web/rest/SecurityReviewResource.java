package com.ccreanga.planning.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ccreanga.planning.domain.SecurityReview;
import com.ccreanga.planning.service.SecurityReviewService;
import com.ccreanga.planning.web.rest.util.HeaderUtil;
import com.ccreanga.planning.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SecurityReview.
 */
@RestController
@RequestMapping("/api")
public class SecurityReviewResource {

    private final Logger log = LoggerFactory.getLogger(SecurityReviewResource.class);
        
    @Inject
    private SecurityReviewService securityReviewService;
    
    /**
     * POST  /securityReviews -> Create a new securityReview.
     */
    @RequestMapping(value = "/securityReviews",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecurityReview> createSecurityReview(@Valid @RequestBody SecurityReview securityReview) throws URISyntaxException {
        log.debug("REST request to save SecurityReview : {}", securityReview);
        if (securityReview.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("securityReview", "idexists", "A new securityReview cannot already have an ID")).body(null);
        }
        SecurityReview result = securityReviewService.save(securityReview);
        return ResponseEntity.created(new URI("/api/securityReviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("securityReview", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /securityReviews -> Updates an existing securityReview.
     */
    @RequestMapping(value = "/securityReviews",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecurityReview> updateSecurityReview(@Valid @RequestBody SecurityReview securityReview) throws URISyntaxException {
        log.debug("REST request to update SecurityReview : {}", securityReview);
        if (securityReview.getId() == null) {
            return createSecurityReview(securityReview);
        }
        SecurityReview result = securityReviewService.save(securityReview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("securityReview", securityReview.getId().toString()))
            .body(result);
    }

    /**
     * GET  /securityReviews -> get all the securityReviews.
     */
    @RequestMapping(value = "/securityReviews",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SecurityReview>> getAllSecurityReviews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SecurityReviews");
        Page<SecurityReview> page = securityReviewService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/securityReviews");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /securityReviews/:id -> get the "id" securityReview.
     */
    @RequestMapping(value = "/securityReviews/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SecurityReview> getSecurityReview(@PathVariable Long id) {
        log.debug("REST request to get SecurityReview : {}", id);
        SecurityReview securityReview = securityReviewService.findOne(id);
        return Optional.ofNullable(securityReview)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /securityReviews/:id -> delete the "id" securityReview.
     */
    @RequestMapping(value = "/securityReviews/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSecurityReview(@PathVariable Long id) {
        log.debug("REST request to delete SecurityReview : {}", id);
        securityReviewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("securityReview", id.toString())).build();
    }
}
