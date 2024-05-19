package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SkillSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SkillSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet, Long>, JpaSpecificationExecutor<SkillSet> {}
