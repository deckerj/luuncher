package com.luuncher.service.impl;

import com.luuncher.service.LunchService;
import com.luuncher.domain.Lunch;
import com.luuncher.repository.LunchRepository;
import com.luuncher.web.rest.dto.LunchDTO;
import com.luuncher.web.rest.mapper.LunchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lunch.
 */
@Service
@Transactional
public class LunchServiceImpl implements LunchService{

    private final Logger log = LoggerFactory.getLogger(LunchServiceImpl.class);
    
    @Inject
    private LunchRepository lunchRepository;
    
    @Inject
    private LunchMapper lunchMapper;
    
    /**
     * Save a lunch.
     * 
     * @param lunchDTO the entity to save
     * @return the persisted entity
     */
    public LunchDTO save(LunchDTO lunchDTO) {
        log.debug("Request to save Lunch : {}", lunchDTO);
        Lunch lunch = lunchMapper.lunchDTOToLunch(lunchDTO);
        lunch = lunchRepository.save(lunch);
        LunchDTO result = lunchMapper.lunchToLunchDTO(lunch);
        return result;
    }

    /**
     *  Get all the lunches.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LunchDTO> findAll() {
        log.debug("Request to get all Lunches");
        List<LunchDTO> result = lunchRepository.findAll().stream()
            .map(lunchMapper::lunchToLunchDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one lunch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LunchDTO findOne(Long id) {
        log.debug("Request to get Lunch : {}", id);
        Lunch lunch = lunchRepository.findOne(id);
        LunchDTO lunchDTO = lunchMapper.lunchToLunchDTO(lunch);
        return lunchDTO;
    }

    /**
     *  Delete the  lunch by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lunch : {}", id);
        lunchRepository.delete(id);
    }
}
