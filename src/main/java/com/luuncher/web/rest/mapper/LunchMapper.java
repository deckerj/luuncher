package com.luuncher.web.rest.mapper;

import com.luuncher.domain.*;
import com.luuncher.web.rest.dto.LunchDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Lunch and its DTO LunchDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LunchMapper {

    LunchDTO lunchToLunchDTO(Lunch lunch);

    List<LunchDTO> lunchesToLunchDTOs(List<Lunch> lunches);

    @Mapping(target = "people", ignore = true)
    Lunch lunchDTOToLunch(LunchDTO lunchDTO);

    List<Lunch> lunchDTOsToLunches(List<LunchDTO> lunchDTOs);
}
