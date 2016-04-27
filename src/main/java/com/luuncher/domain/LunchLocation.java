package com.luuncher.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LunchLocation.
 */
@Entity
@Table(name = "lunch_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LunchLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lunch_location_id")
    private Long lunchLocationId;

    @Column(name = "lunch_location_name")
    private String lunchLocationName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLunchLocationId() {
        return lunchLocationId;
    }

    public void setLunchLocationId(Long lunchLocationId) {
        this.lunchLocationId = lunchLocationId;
    }

    public String getLunchLocationName() {
        return lunchLocationName;
    }

    public void setLunchLocationName(String lunchLocationName) {
        this.lunchLocationName = lunchLocationName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LunchLocation lunchLocation = (LunchLocation) o;
        if(lunchLocation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lunchLocation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchLocation{" +
            "id=" + id +
            ", lunchLocationId='" + lunchLocationId + "'" +
            ", lunchLocationName='" + lunchLocationName + "'" +
            ", streetAddress='" + streetAddress + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            '}';
    }
}
