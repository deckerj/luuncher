package com.luuncher.web.rest.mapper;

import com.luuncher.domain.*;
import com.luuncher.web.rest.dto.LunchEventDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LunchEvent and its DTO LunchEventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LunchEventMapper {

    @Mapping(source = "lunchLocation.id", target = "lunchLocationId")
    @Mapping(source = "lunchGroup.id", target = "lunchGroupId")
    @Mapping(source = "lunchGroup.name", target = "lunchGroupName")
    LunchEventDTO lunchEventToLunchEventDTO(LunchEvent lunchEvent);

    List<LunchEventDTO> lunchEventsToLunchEventDTOs(List<LunchEvent> lunchEvents);

    @Mapping(source = "lunchLocationId", target = "lunchLocation")
    @Mapping(source = "lunchGroupId", target = "lunchGroup")
    LunchEvent lunchEventDTOToLunchEvent(LunchEventDTO lunchEventDTO);

    List<LunchEvent> lunchEventDTOsToLunchEvents(List<LunchEventDTO> lunchEventDTOs);

    default LunchLocation lunchLocationFromId(Long id) {
        if (id == null) {
            return null;
        }
        LunchLocation lunchLocation = new LunchLocation();
        lunchLocation.setId(id);
        return lunchLocation;
    }

    default LunchGroup lunchGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        LunchGroup lunchGroup = new LunchGroup();
        lunchGroup.setId(id);
        return lunchGroup;
    }
}
