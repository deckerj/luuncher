package com.luuncher.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luuncher.domain.LunchEvent;
import com.luuncher.service.LunchEventService;
import com.luuncher.web.rest.util.HeaderUtil;
import com.luuncher.web.rest.dto.LunchEventDTO;
import com.luuncher.web.rest.mapper.LunchEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing LunchEvent.
 */
@RestController
@RequestMapping("/api")
public class LunchEventResource {

    private final Logger log = LoggerFactory.getLogger(LunchEventResource.class);
        
    @Inject
    private LunchEventService lunchEventService;
    
    @Inject
    private LunchEventMapper lunchEventMapper;
    
    /**
     * POST  /lunch-events : Create a new lunchEvent.
     *
     * @param lunchEventDTO the lunchEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lunchEventDTO, or with status 400 (Bad Request) if the lunchEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-events",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchEventDTO> createLunchEvent(@RequestBody LunchEventDTO lunchEventDTO) throws URISyntaxException {
        log.debug("REST request to save LunchEvent : {}", lunchEventDTO);
        if (lunchEventDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lunchEvent", "idexists", "A new lunchEvent cannot already have an ID")).body(null);
        }
        LunchEventDTO result = lunchEventService.save(lunchEventDTO);
        return ResponseEntity.created(new URI("/api/lunch-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lunchEvent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lunch-events : Updates an existing lunchEvent.
     *
     * @param lunchEventDTO the lunchEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lunchEventDTO,
     * or with status 400 (Bad Request) if the lunchEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the lunchEventDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-events",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchEventDTO> updateLunchEvent(@RequestBody LunchEventDTO lunchEventDTO) throws URISyntaxException {
        log.debug("REST request to update LunchEvent : {}", lunchEventDTO);
        if (lunchEventDTO.getId() == null) {
            return createLunchEvent(lunchEventDTO);
        }
        LunchEventDTO result = lunchEventService.save(lunchEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lunchEvent", lunchEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lunch-events : get all the lunchEvents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lunchEvents in body
     */
    @RequestMapping(value = "/lunch-events",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<LunchEventDTO> getAllLunchEvents() {
        log.debug("REST request to get all LunchEvents");
        return lunchEventService.findAll();
    }

    /**
     * GET  /lunch-events/:id : get the "id" lunchEvent.
     *
     * @param id the id of the lunchEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lunchEventDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lunch-events/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchEventDTO> getLunchEvent(@PathVariable Long id) {
        log.debug("REST request to get LunchEvent : {}", id);
        LunchEventDTO lunchEventDTO = lunchEventService.findOne(id);
        return Optional.ofNullable(lunchEventDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lunch-events/:id : delete the "id" lunchEvent.
     *
     * @param id the id of the lunchEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lunch-events/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLunchEvent(@PathVariable Long id) {
        log.debug("REST request to delete LunchEvent : {}", id);
        lunchEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lunchEvent", id.toString())).build();
    }

}
