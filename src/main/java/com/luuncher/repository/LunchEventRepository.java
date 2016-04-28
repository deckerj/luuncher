package com.luuncher.repository;

import com.luuncher.domain.LunchEvent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LunchEvent entity.
 */
public interface LunchEventRepository extends JpaRepository<LunchEvent,Long> {

}
