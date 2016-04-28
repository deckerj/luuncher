package com.luuncher.web.rest.mapper;

import com.luuncher.domain.*;
import com.luuncher.web.rest.dto.PersonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper {

    PersonDTO personToPersonDTO(Person person);

    List<PersonDTO> peopleToPersonDTOs(List<Person> people);

    @Mapping(target = "lunchGroups", ignore = true)
    Person personDTOToPerson(PersonDTO personDTO);

    List<Person> personDTOsToPeople(List<PersonDTO> personDTOs);
}
