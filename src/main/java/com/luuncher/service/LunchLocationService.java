package com.luuncher.service;

import com.luuncher.domain.LunchLocation;
import com.luuncher.web.rest.dto.LunchLocationDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing LunchLocation.
 */
public interface LunchLocationService {

    /**
     * Save a lunchLocation.
     * 
     * @param lunchLocationDTO the entity to save
     * @return the persisted entity
     */
    LunchLocationDTO save(LunchLocationDTO lunchLocationDTO);

    /**
     *  Get all the lunchLocations.
     *  
     *  @return the list of entities
     */
    List<LunchLocationDTO> findAll();

    /**
     *  Get the "id" lunchLocation.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    LunchLocationDTO findOne(Long id);

    /**
     *  Delete the "id" lunchLocation.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
