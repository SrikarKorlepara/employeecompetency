package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EmployeeTraining;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeTraining entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTrainingRepository extends JpaRepository<EmployeeTraining, Long>, JpaSpecificationExecutor<EmployeeTraining> {}
