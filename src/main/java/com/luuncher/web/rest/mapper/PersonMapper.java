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

    @Mapping(source = "lunch.id", target = "lunchId")
    PersonDTO personToPersonDTO(Person person);

    List<PersonDTO> peopleToPersonDTOs(List<Person> people);

    @Mapping(source = "lunchId", target = "lunch")
    Person personDTOToPerson(PersonDTO personDTO);

    List<Person> personDTOsToPeople(List<PersonDTO> personDTOs);

    default Lunch lunchFromId(Long id) {
        if (id == null) {
            return null;
        }
        Lunch lunch = new Lunch();
        lunch.setId(id);
        return lunch;
    }
}
