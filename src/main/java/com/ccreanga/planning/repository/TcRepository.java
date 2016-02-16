package com.ccreanga.planning.repository;

import com.ccreanga.planning.domain.Tc;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tc entity.
 */
public interface TcRepository extends JpaRepository<Tc,Long> {

    @Query("select distinct tc from Tc tc left join fetch tc.notess left join fetch tc.reviewerss left join fetch tc.linkss")
    List<Tc> findAllWithEagerRelationships();

    @Query("select tc from Tc tc left join fetch tc.notess left join fetch tc.reviewerss left join fetch tc.linkss where tc.id =:id")
    Tc findOneWithEagerRelationships(@Param("id") Long id);

}
