package com.luuncher.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the LunchEvent entity.
 */
public class LunchEventDTO implements Serializable {

    private Long id;

    private LocalDate startDate;


    private Long lunchLocationId;
    private Long lunchGroupId;

    private String lunchGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getLunchLocationId() {
        return lunchLocationId;
    }

    public void setLunchLocationId(Long lunchLocationId) {
        this.lunchLocationId = lunchLocationId;
    }
    public Long getLunchGroupId() {
        return lunchGroupId;
    }

    public void setLunchGroupId(Long lunchGroupId) {
        this.lunchGroupId = lunchGroupId;
    }

    public String getLunchGroupName() {
        return lunchGroupName;
    }

    public void setLunchGroupName(String lunchGroupName) {
        this.lunchGroupName = lunchGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LunchEventDTO lunchEventDTO = (LunchEventDTO) o;

        if ( ! Objects.equals(id, lunchEventDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchEventDTO{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            '}';
    }
}
