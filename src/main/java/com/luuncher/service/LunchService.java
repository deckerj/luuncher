package com.luuncher.service;

import com.luuncher.domain.Lunch;
import com.luuncher.web.rest.dto.LunchDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Lunch.
 */
public interface LunchService {

    /**
     * Save a lunch.
     * 
     * @param lunchDTO the entity to save
     * @return the persisted entity
     */
    LunchDTO save(LunchDTO lunchDTO);

    /**
     *  Get all the lunches.
     *  
     *  @return the list of entities
     */
    List<LunchDTO> findAll();

    /**
     *  Get the "id" lunch.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    LunchDTO findOne(Long id);

    /**
     *  Delete the "id" lunch.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
