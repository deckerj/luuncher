package com.luuncher.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luuncher.domain.LunchLocation;
import com.luuncher.service.LunchLocationService;
import com.luuncher.web.rest.util.HeaderUtil;
import com.luuncher.web.rest.dto.LunchLocationDTO;
import com.luuncher.web.rest.mapper.LunchLocationMapper;
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
 * REST controller for managing LunchLocation.
 */
@RestController
@RequestMapping("/api")
public class LunchLocationResource {

    private final Logger log = LoggerFactory.getLogger(LunchLocationResource.class);
        
    @Inject
    private LunchLocationService lunchLocationService;
    
    @Inject
    private LunchLocationMapper lunchLocationMapper;
    
    /**
     * POST  /lunch-locations : Create a new lunchLocation.
     *
     * @param lunchLocationDTO the lunchLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lunchLocationDTO, or with status 400 (Bad Request) if the lunchLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-locations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchLocationDTO> createLunchLocation(@RequestBody LunchLocationDTO lunchLocationDTO) throws URISyntaxException {
        log.debug("REST request to save LunchLocation : {}", lunchLocationDTO);
        if (lunchLocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lunchLocation", "idexists", "A new lunchLocation cannot already have an ID")).body(null);
        }
        LunchLocationDTO result = lunchLocationService.save(lunchLocationDTO);
        return ResponseEntity.created(new URI("/api/lunch-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lunchLocation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lunch-locations : Updates an existing lunchLocation.
     *
     * @param lunchLocationDTO the lunchLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lunchLocationDTO,
     * or with status 400 (Bad Request) if the lunchLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the lunchLocationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-locations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchLocationDTO> updateLunchLocation(@RequestBody LunchLocationDTO lunchLocationDTO) throws URISyntaxException {
        log.debug("REST request to update LunchLocation : {}", lunchLocationDTO);
        if (lunchLocationDTO.getId() == null) {
            return createLunchLocation(lunchLocationDTO);
        }
        LunchLocationDTO result = lunchLocationService.save(lunchLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lunchLocation", lunchLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lunch-locations : get all the lunchLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lunchLocations in body
     */
    @RequestMapping(value = "/lunch-locations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<LunchLocationDTO> getAllLunchLocations() {
        log.debug("REST request to get all LunchLocations");
        return lunchLocationService.findAll();
    }

    /**
     * GET  /lunch-locations/:id : get the "id" lunchLocation.
     *
     * @param id the id of the lunchLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lunchLocationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lunch-locations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchLocationDTO> getLunchLocation(@PathVariable Long id) {
        log.debug("REST request to get LunchLocation : {}", id);
        LunchLocationDTO lunchLocationDTO = lunchLocationService.findOne(id);
        return Optional.ofNullable(lunchLocationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lunch-locations/:id : delete the "id" lunchLocation.
     *
     * @param id the id of the lunchLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lunch-locations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLunchLocation(@PathVariable Long id) {
        log.debug("REST request to delete LunchLocation : {}", id);
        lunchLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lunchLocation", id.toString())).build();
    }

}
