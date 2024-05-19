package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PerformanceReview;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerformanceReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long>, JpaSpecificationExecutor<PerformanceReview> {}
