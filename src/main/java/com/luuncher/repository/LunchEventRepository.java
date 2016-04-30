package com.luuncher.repository;

import com.luuncher.domain.LunchEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LunchEvent entity.
 */
public interface LunchEventRepository extends JpaRepository<LunchEvent,Long> {
    
    @Query("select le from LunchEvent le join fetch le.lunchGroup lg join fetch lg.people p join fetch p.user u where u.login = ?#{principal}")
    public List<LunchEvent> findByUserIsCurrentUser();
}
