package com.luuncher.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LunchGroup.
 */
@Entity
@Table(name = "lunch_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LunchGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "lunchGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LunchEvent> lunchEvents = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lunch_group_person",
               joinColumns = @JoinColumn(name="lunch_groups_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="people_id", referencedColumnName="ID"))
    private Set<Person> people = new HashSet<>();

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

    public Set<LunchEvent> getLunchEvents() {
        return lunchEvents;
    }

    public void setLunchEvents(Set<LunchEvent> lunchEvents) {
        this.lunchEvents = lunchEvents;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
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
        LunchGroup lunchGroup = (LunchGroup) o;
        if(lunchGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lunchGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LunchGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
