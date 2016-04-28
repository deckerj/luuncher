package com.luuncher.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the LunchGroup entity.
 */
public class LunchGroupDTO implements Serializable {

    private Long id;

    private String name;


    private Set<PersonDTO> people = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PersonDTO> getPeople() {
        return people;
    }

    public void setPeople(Set<PersonDTO> people) {
        this.people = people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LunchGroupDTO lunchGroupDTO = (LunchGroupDTO) o;

        if ( ! Objects.equals(id, lunchGroupDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchGroupDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
