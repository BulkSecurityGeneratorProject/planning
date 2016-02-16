package com.ccreanga.planning.repository;

import com.ccreanga.planning.domain.Link;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Link entity.
 */
public interface LinkRepository extends JpaRepository<Link,Long> {

}
