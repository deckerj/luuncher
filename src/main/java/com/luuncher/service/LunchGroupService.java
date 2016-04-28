package com.luuncher.service;

import com.luuncher.domain.LunchGroup;
import com.luuncher.web.rest.dto.LunchGroupDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing LunchGroup.
 */
public interface LunchGroupService {

    /**
     * Save a lunchGroup.
     * 
     * @param lunchGroupDTO the entity to save
     * @return the persisted entity
     */
    LunchGroupDTO save(LunchGroupDTO lunchGroupDTO);

    /**
     *  Get all the lunchGroups.
     *  
     *  @return the list of entities
     */
    List<LunchGroupDTO> findAll();

    /**
     *  Get the "id" lunchGroup.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    LunchGroupDTO findOne(Long id);

    /**
     *  Delete the "id" lunchGroup.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
