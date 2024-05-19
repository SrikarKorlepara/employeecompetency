package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Competency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Competency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long>, JpaSpecificationExecutor<Competency> {}
