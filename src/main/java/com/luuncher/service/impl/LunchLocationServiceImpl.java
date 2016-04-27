package com.luuncher.service.impl;

import com.luuncher.service.LunchLocationService;
import com.luuncher.domain.LunchLocation;
import com.luuncher.repository.LunchLocationRepository;
import com.luuncher.web.rest.dto.LunchLocationDTO;
import com.luuncher.web.rest.mapper.LunchLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LunchLocation.
 */
@Service
@Transactional
public class LunchLocationServiceImpl implements LunchLocationService{

    private final Logger log = LoggerFactory.getLogger(LunchLocationServiceImpl.class);
    
    @Inject
    private LunchLocationRepository lunchLocationRepository;
    
    @Inject
    private LunchLocationMapper lunchLocationMapper;
    
    /**
     * Save a lunchLocation.
     * 
     * @param lunchLocationDTO the entity to save
     * @return the persisted entity
     */
    public LunchLocationDTO save(LunchLocationDTO lunchLocationDTO) {
        log.debug("Request to save LunchLocation : {}", lunchLocationDTO);
        LunchLocation lunchLocation = lunchLocationMapper.lunchLocationDTOToLunchLocation(lunchLocationDTO);
        lunchLocation = lunchLocationRepository.save(lunchLocation);
        LunchLocationDTO result = lunchLocationMapper.lunchLocationToLunchLocationDTO(lunchLocation);
        return result;
    }

    /**
     *  Get all the lunchLocations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LunchLocationDTO> findAll() {
        log.debug("Request to get all LunchLocations");
        List<LunchLocationDTO> result = lunchLocationRepository.findAll().stream()
            .map(lunchLocationMapper::lunchLocationToLunchLocationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one lunchLocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LunchLocationDTO findOne(Long id) {
        log.debug("Request to get LunchLocation : {}", id);
        LunchLocation lunchLocation = lunchLocationRepository.findOne(id);
        LunchLocationDTO lunchLocationDTO = lunchLocationMapper.lunchLocationToLunchLocationDTO(lunchLocation);
        return lunchLocationDTO;
    }

    /**
     *  Delete the  lunchLocation by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LunchLocation : {}", id);
        lunchLocationRepository.delete(id);
    }
}
