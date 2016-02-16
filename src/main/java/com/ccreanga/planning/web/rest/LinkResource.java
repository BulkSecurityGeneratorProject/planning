package com.ccreanga.planning.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ccreanga.planning.domain.Link;
import com.ccreanga.planning.service.LinkService;
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
 * REST controller for managing Link.
 */
@RestController
@RequestMapping("/api")
public class LinkResource {

    private final Logger log = LoggerFactory.getLogger(LinkResource.class);
        
    @Inject
    private LinkService linkService;
    
    /**
     * POST  /links -> Create a new link.
     */
    @RequestMapping(value = "/links",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Link> createLink(@Valid @RequestBody Link link) throws URISyntaxException {
        log.debug("REST request to save Link : {}", link);
        if (link.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("link", "idexists", "A new link cannot already have an ID")).body(null);
        }
        Link result = linkService.save(link);
        return ResponseEntity.created(new URI("/api/links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("link", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /links -> Updates an existing link.
     */
    @RequestMapping(value = "/links",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Link> updateLink(@Valid @RequestBody Link link) throws URISyntaxException {
        log.debug("REST request to update Link : {}", link);
        if (link.getId() == null) {
            return createLink(link);
        }
        Link result = linkService.save(link);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("link", link.getId().toString()))
            .body(result);
    }

    /**
     * GET  /links -> get all the links.
     */
    @RequestMapping(value = "/links",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Link>> getAllLinks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Links");
        Page<Link> page = linkService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/links");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /links/:id -> get the "id" link.
     */
    @RequestMapping(value = "/links/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Link> getLink(@PathVariable Long id) {
        log.debug("REST request to get Link : {}", id);
        Link link = linkService.findOne(id);
        return Optional.ofNullable(link)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /links/:id -> delete the "id" link.
     */
    @RequestMapping(value = "/links/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        log.debug("REST request to delete Link : {}", id);
        linkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("link", id.toString())).build();
    }
}
