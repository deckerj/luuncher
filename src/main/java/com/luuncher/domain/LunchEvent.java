package com.luuncher.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A LunchEvent.
 */
@Entity
@Table(name = "lunch_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LunchEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @OneToOne
    @JoinColumn(unique = true)
    private LunchLocation lunchLocation;

    @ManyToOne
    private LunchGroup lunchGroup;

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

    public LunchLocation getLunchLocation() {
        return lunchLocation;
    }

    public void setLunchLocation(LunchLocation lunchLocation) {
        this.lunchLocation = lunchLocation;
    }

    public LunchGroup getLunchGroup() {
        return lunchGroup;
    }

    public void setLunchGroup(LunchGroup lunchGroup) {
        this.lunchGroup = lunchGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LunchEvent lunchEvent = (LunchEvent) o;
        if(lunchEvent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lunchEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchEvent{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            '}';
    }
}
