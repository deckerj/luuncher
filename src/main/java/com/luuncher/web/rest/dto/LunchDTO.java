package com.luuncher.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Lunch entity.
 */
public class LunchDTO implements Serializable {

    private Long id;

    private Long lunchId;


    private LocalDate start;


    private LocalDate end;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getLunchId() {
        return lunchId;
    }

    public void setLunchId(Long lunchId) {
        this.lunchId = lunchId;
    }
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }
    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LunchDTO lunchDTO = (LunchDTO) o;

        if ( ! Objects.equals(id, lunchDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchDTO{" +
            "id=" + id +
            ", lunchId='" + lunchId + "'" +
            ", start='" + start + "'" +
            ", end='" + end + "'" +
            '}';
    }
}
