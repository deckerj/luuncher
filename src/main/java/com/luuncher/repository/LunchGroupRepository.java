package com.luuncher.repository;

import com.luuncher.domain.LunchGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LunchGroup entity.
 */
public interface LunchGroupRepository extends JpaRepository<LunchGroup,Long> {

    @Query("select distinct lunchGroup from LunchGroup lunchGroup left join fetch lunchGroup.people")
    List<LunchGroup> findAllWithEagerRelationships();

    @Query("select lunchGroup from LunchGroup lunchGroup left join fetch lunchGroup.people where lunchGroup.id =:id")
    LunchGroup findOneWithEagerRelationships(@Param("id") Long id);

}
