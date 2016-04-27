package com.luuncher.repository;

import com.luuncher.domain.LunchLocation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LunchLocation entity.
 */
public interface LunchLocationRepository extends JpaRepository<LunchLocation,Long> {

}
