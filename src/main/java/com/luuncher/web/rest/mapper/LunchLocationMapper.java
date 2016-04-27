package com.luuncher.web.rest.mapper;

import com.luuncher.domain.*;
import com.luuncher.web.rest.dto.LunchLocationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LunchLocation and its DTO LunchLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LunchLocationMapper {

    LunchLocationDTO lunchLocationToLunchLocationDTO(LunchLocation lunchLocation);

    List<LunchLocationDTO> lunchLocationsToLunchLocationDTOs(List<LunchLocation> lunchLocations);

    LunchLocation lunchLocationDTOToLunchLocation(LunchLocationDTO lunchLocationDTO);

    List<LunchLocation> lunchLocationDTOsToLunchLocations(List<LunchLocationDTO> lunchLocationDTOs);
}
