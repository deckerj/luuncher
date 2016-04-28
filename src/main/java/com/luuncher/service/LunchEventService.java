package com.luuncher.service;

import com.luuncher.domain.LunchEvent;
import com.luuncher.web.rest.dto.LunchEventDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing LunchEvent.
 */
public interface LunchEventService {

    /**
     * Save a lunchEvent.
     * 
     * @param lunchEventDTO the entity to save
     * @return the persisted entity
     */
    LunchEventDTO save(LunchEventDTO lunchEventDTO);

    /**
     *  Get all the lunchEvents.
     *  
     *  @return the list of entities
     */
    List<LunchEventDTO> findAll();

    /**
     *  Get the "id" lunchEvent.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    LunchEventDTO findOne(Long id);

    /**
     *  Delete the "id" lunchEvent.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
