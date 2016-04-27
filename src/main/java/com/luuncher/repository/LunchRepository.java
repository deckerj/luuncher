package com.luuncher.repository;

import com.luuncher.domain.Lunch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lunch entity.
 */
public interface LunchRepository extends JpaRepository<Lunch,Long> {

}
