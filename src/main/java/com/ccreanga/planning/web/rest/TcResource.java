package com.ccreanga.planning.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ccreanga.planning.domain.Tc;
import com.ccreanga.planning.service.TcService;
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
 * REST controller for managing Tc.
 */
@RestController
@RequestMapping("/api")
public class TcResource {

    private final Logger log = LoggerFactory.getLogger(TcResource.class);
        
    @Inject
    private TcService tcService;
    
    /**
     * POST  /tcs -> Create a new tc.
     */
    @RequestMapping(value = "/tcs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tc> createTc(@Valid @RequestBody Tc tc) throws URISyntaxException {
        log.debug("REST request to save Tc : {}", tc);
        if (tc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tc", "idexists", "A new tc cannot already have an ID")).body(null);
        }
        Tc result = tcService.save(tc);
        return ResponseEntity.created(new URI("/api/tcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tcs -> Updates an existing tc.
     */
    @RequestMapping(value = "/tcs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tc> updateTc(@Valid @RequestBody Tc tc) throws URISyntaxException {
        log.debug("REST request to update Tc : {}", tc);
        if (tc.getId() == null) {
            return createTc(tc);
        }
        Tc result = tcService.save(tc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tc", tc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tcs -> get all the tcs.
     */
    @RequestMapping(value = "/tcs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tc>> getAllTcs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tcs");
        Page<Tc> page = tcService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tcs/:id -> get the "id" tc.
     */
    @RequestMapping(value = "/tcs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tc> getTc(@PathVariable Long id) {
        log.debug("REST request to get Tc : {}", id);
        Tc tc = tcService.findOne(id);
        return Optional.ofNullable(tc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tcs/:id -> delete the "id" tc.
     */
    @RequestMapping(value = "/tcs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTc(@PathVariable Long id) {
        log.debug("REST request to delete Tc : {}", id);
        tcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tc", id.toString())).build();
    }
}
