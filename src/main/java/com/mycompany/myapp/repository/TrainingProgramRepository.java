package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TrainingProgram;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TrainingProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>, JpaSpecificationExecutor<TrainingProgram> {}
