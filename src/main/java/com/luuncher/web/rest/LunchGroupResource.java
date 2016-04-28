package com.luuncher.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.luuncher.domain.LunchGroup;
import com.luuncher.service.LunchGroupService;
import com.luuncher.web.rest.util.HeaderUtil;
import com.luuncher.web.rest.dto.LunchGroupDTO;
import com.luuncher.web.rest.mapper.LunchGroupMapper;
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
 * REST controller for managing LunchGroup.
 */
@RestController
@RequestMapping("/api")
public class LunchGroupResource {

    private final Logger log = LoggerFactory.getLogger(LunchGroupResource.class);
        
    @Inject
    private LunchGroupService lunchGroupService;
    
    @Inject
    private LunchGroupMapper lunchGroupMapper;
    
    /**
     * POST  /lunch-groups : Create a new lunchGroup.
     *
     * @param lunchGroupDTO the lunchGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lunchGroupDTO, or with status 400 (Bad Request) if the lunchGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-groups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchGroupDTO> createLunchGroup(@RequestBody LunchGroupDTO lunchGroupDTO) throws URISyntaxException {
        log.debug("REST request to save LunchGroup : {}", lunchGroupDTO);
        if (lunchGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lunchGroup", "idexists", "A new lunchGroup cannot already have an ID")).body(null);
        }
        LunchGroupDTO result = lunchGroupService.save(lunchGroupDTO);
        return ResponseEntity.created(new URI("/api/lunch-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lunchGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lunch-groups : Updates an existing lunchGroup.
     *
     * @param lunchGroupDTO the lunchGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lunchGroupDTO,
     * or with status 400 (Bad Request) if the lunchGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the lunchGroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/lunch-groups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchGroupDTO> updateLunchGroup(@RequestBody LunchGroupDTO lunchGroupDTO) throws URISyntaxException {
        log.debug("REST request to update LunchGroup : {}", lunchGroupDTO);
        if (lunchGroupDTO.getId() == null) {
            return createLunchGroup(lunchGroupDTO);
        }
        LunchGroupDTO result = lunchGroupService.save(lunchGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lunchGroup", lunchGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lunch-groups : get all the lunchGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lunchGroups in body
     */
    @RequestMapping(value = "/lunch-groups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<LunchGroupDTO> getAllLunchGroups() {
        log.debug("REST request to get all LunchGroups");
        return lunchGroupService.findAll();
    }

    /**
     * GET  /lunch-groups/:id : get the "id" lunchGroup.
     *
     * @param id the id of the lunchGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lunchGroupDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/lunch-groups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LunchGroupDTO> getLunchGroup(@PathVariable Long id) {
        log.debug("REST request to get LunchGroup : {}", id);
        LunchGroupDTO lunchGroupDTO = lunchGroupService.findOne(id);
        return Optional.ofNullable(lunchGroupDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lunch-groups/:id : delete the "id" lunchGroup.
     *
     * @param id the id of the lunchGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/lunch-groups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLunchGroup(@PathVariable Long id) {
        log.debug("REST request to delete LunchGroup : {}", id);
        lunchGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lunchGroup", id.toString())).build();
    }

}
