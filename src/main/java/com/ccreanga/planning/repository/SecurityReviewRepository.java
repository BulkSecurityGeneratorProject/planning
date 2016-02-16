package com.ccreanga.planning.repository;

import com.ccreanga.planning.domain.SecurityReview;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SecurityReview entity.
 */
public interface SecurityReviewRepository extends JpaRepository<SecurityReview,Long> {

    @Query("select distinct securityReview from SecurityReview securityReview left join fetch securityReview.notess left join fetch securityReview.reviewerss left join fetch securityReview.linkss")
    List<SecurityReview> findAllWithEagerRelationships();

    @Query("select securityReview from SecurityReview securityReview left join fetch securityReview.notess left join fetch securityReview.reviewerss left join fetch securityReview.linkss where securityReview.id =:id")
    SecurityReview findOneWithEagerRelationships(@Param("id") Long id);

}
