package com.luuncher.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luuncher.domain.Lunch;
import com.luuncher.service.LunchService;
import com.luuncher.web.rest.util.HeaderUtil;
import com.luuncher.web.rest.dto.LunchDTO;
import com.luuncher.web.rest.mapper.LunchMapper;
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
 * REST controller for managing Lunch.
 */
@RestController
@RequestMapping("/api")
public class LunchResource {

    private final Logger log = LoggerFactory.getLogger(LunchResource.class);
        
    @Inject
    private LunchService lunchService;
    
    @Inject
    private LunchMapper lunchMapper;
    
    /**
     * POST  /lunches : Create a new lunch.
     *
     * @param lunchDTO the lunchDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lunchDTO, or with status 400 (Bad Request) if the lunch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunches",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchDTO> createLunch(@RequestBody LunchDTO lunchDTO) throws URISyntaxException {
        log.debug("REST request to save Lunch : {}", lunchDTO);
        if (lunchDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lunch", "idexists", "A new lunch cannot already have an ID")).body(null);
        }
        LunchDTO result = lunchService.save(lunchDTO);
        return ResponseEntity.created(new URI("/api/lunches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lunch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lunches : Updates an existing lunch.
     *
     * @param lunchDTO the lunchDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lunchDTO,
     * or with status 400 (Bad Request) if the lunchDTO is not valid,
     * or with status 500 (Internal Server Error) if the lunchDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunches",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchDTO> updateLunch(@RequestBody LunchDTO lunchDTO) throws URISyntaxException {
        log.debug("REST request to update Lunch : {}", lunchDTO);
        if (lunchDTO.getId() == null) {
            return createLunch(lunchDTO);
        }
        LunchDTO result = lunchService.save(lunchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lunch", lunchDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lunches : get all the lunches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lunches in body
     */
    @RequestMapping(value = "/lunches",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<LunchDTO> getAllLunches() {
        log.debug("REST request to get all Lunches");
        return lunchService.findAll();
    }

    /**
     * GET  /lunches/:id : get the "id" lunch.
     *
     * @param id the id of the lunchDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lunchDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lunches/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchDTO> getLunch(@PathVariable Long id) {
        log.debug("REST request to get Lunch : {}", id);
        LunchDTO lunchDTO = lunchService.findOne(id);
        return Optional.ofNullable(lunchDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lunches/:id : delete the "id" lunch.
     *
     * @param id the id of the lunchDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lunches/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLunch(@PathVariable Long id) {
        log.debug("REST request to delete Lunch : {}", id);
        lunchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lunch", id.toString())).build();
    }

}
