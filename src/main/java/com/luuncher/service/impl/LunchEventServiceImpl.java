package com.luuncher.service.impl;

import com.luuncher.service.LunchEventService;
import com.luuncher.domain.LunchEvent;
import com.luuncher.repository.LunchEventRepository;
import com.luuncher.web.rest.dto.LunchEventDTO;
import com.luuncher.web.rest.mapper.LunchEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LunchEvent.
 */
@Service
@Transactional
public class LunchEventServiceImpl implements LunchEventService{

    private final Logger log = LoggerFactory.getLogger(LunchEventServiceImpl.class);
    
    @Inject
    private LunchEventRepository lunchEventRepository;
    
    @Inject
    private LunchEventMapper lunchEventMapper;
    
    /**
     * Save a lunchEvent.
     * 
     * @param lunchEventDTO the entity to save
     * @return the persisted entity
     */
    public LunchEventDTO save(LunchEventDTO lunchEventDTO) {
        log.debug("Request to save LunchEvent : {}", lunchEventDTO);
        LunchEvent lunchEvent = lunchEventMapper.lunchEventDTOToLunchEvent(lunchEventDTO);
        lunchEvent = lunchEventRepository.save(lunchEvent);
        LunchEventDTO result = lunchEventMapper.lunchEventToLunchEventDTO(lunchEvent);
        return result;
    }

    /**
     *  Get all the lunchEvents.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LunchEventDTO> findAll() {
        log.debug("Request to get all LunchEvents");
        List<LunchEventDTO> result = lunchEventRepository.findAll().stream()
            .map(lunchEventMapper::lunchEventToLunchEventDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one lunchEvent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LunchEventDTO findOne(Long id) {
        log.debug("Request to get LunchEvent : {}", id);
        LunchEvent lunchEvent = lunchEventRepository.findOne(id);
        LunchEventDTO lunchEventDTO = lunchEventMapper.lunchEventToLunchEventDTO(lunchEvent);
        return lunchEventDTO;
    }

    /**
     *  Delete the  lunchEvent by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LunchEvent : {}", id);
        lunchEventRepository.delete(id);
    }
}
