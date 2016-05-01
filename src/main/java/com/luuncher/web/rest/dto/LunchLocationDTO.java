package com.luuncher.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the LunchLocation entity.
 */
public class LunchLocationDTO implements Serializable {

    private Long id;

    private String name;


    private String streetAddress;


    private String postalCode;


    private String city;


    private String website;


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
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LunchLocationDTO lunchLocationDTO = (LunchLocationDTO) o;

        if ( ! Objects.equals(id, lunchLocationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchLocationDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", streetAddress='" + streetAddress + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            ", website='" + website + "'" +
            '}';
    }
}
