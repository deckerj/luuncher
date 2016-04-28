package com.luuncher.web.rest.mapper;

import com.luuncher.domain.*;
import com.luuncher.web.rest.dto.LunchGroupDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LunchGroup and its DTO LunchGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, })
public interface LunchGroupMapper {

    LunchGroupDTO lunchGroupToLunchGroupDTO(LunchGroup lunchGroup);

    List<LunchGroupDTO> lunchGroupsToLunchGroupDTOs(List<LunchGroup> lunchGroups);

    @Mapping(target = "lunchEvents", ignore = true)
    LunchGroup lunchGroupDTOToLunchGroup(LunchGroupDTO lunchGroupDTO);

    List<LunchGroup> lunchGroupDTOsToLunchGroups(List<LunchGroupDTO> lunchGroupDTOs);

    default Person personFromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
