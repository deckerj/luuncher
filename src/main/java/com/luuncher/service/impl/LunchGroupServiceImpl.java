package com.luuncher.service.impl;

import com.luuncher.service.LunchGroupService;
import com.luuncher.domain.LunchGroup;
import com.luuncher.repository.LunchGroupRepository;
import com.luuncher.web.rest.dto.LunchGroupDTO;
import com.luuncher.web.rest.mapper.LunchGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing LunchGroup.
 */
@Service
@Transactional
public class LunchGroupServiceImpl implements LunchGroupService{

    private final Logger log = LoggerFactory.getLogger(LunchGroupServiceImpl.class);
    
    @Inject
    private LunchGroupRepository lunchGroupRepository;
    
    @Inject
    private LunchGroupMapper lunchGroupMapper;
    
    /**
     * Save a lunchGroup.
     * 
     * @param lunchGroupDTO the entity to save
     * @return the persisted entity
     */
    public LunchGroupDTO save(LunchGroupDTO lunchGroupDTO) {
        log.debug("Request to save LunchGroup : {}", lunchGroupDTO);
        LunchGroup lunchGroup = lunchGroupMapper.lunchGroupDTOToLunchGroup(lunchGroupDTO);
        lunchGroup = lunchGroupRepository.save(lunchGroup);
        LunchGroupDTO result = lunchGroupMapper.lunchGroupToLunchGroupDTO(lunchGroup);
        return result;
    }

    /**
     *  Get all the lunchGroups.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LunchGroupDTO> findAll() {
        log.debug("Request to get all LunchGroups");
        List<LunchGroupDTO> result = lunchGroupRepository.findAllWithEagerRelationships().stream()
            .map(lunchGroupMapper::lunchGroupToLunchGroupDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one lunchGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LunchGroupDTO findOne(Long id) {
        log.debug("Request to get LunchGroup : {}", id);
        LunchGroup lunchGroup = lunchGroupRepository.findOneWithEagerRelationships(id);
        LunchGroupDTO lunchGroupDTO = lunchGroupMapper.lunchGroupToLunchGroupDTO(lunchGroup);
        return lunchGroupDTO;
    }

    /**
     *  Delete the  lunchGroup by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LunchGroup : {}", id);
        lunchGroupRepository.delete(id);
    }
}
