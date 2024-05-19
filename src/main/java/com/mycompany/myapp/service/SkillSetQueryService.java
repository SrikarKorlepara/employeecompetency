package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.SkillSet;
import com.mycompany.myapp.repository.SkillSetRepository;
import com.mycompany.myapp.service.criteria.SkillSetCriteria;
import com.mycompany.myapp.service.dto.SkillSetDTO;
import com.mycompany.myapp.service.mapper.SkillSetMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SkillSet} entities in the database.
 * The main input is a {@link SkillSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SkillSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillSetQueryService extends QueryService<SkillSet> {

    private final Logger log = LoggerFactory.getLogger(SkillSetQueryService.class);

    private final SkillSetRepository skillSetRepository;

    private final SkillSetMapper skillSetMapper;

    public SkillSetQueryService(SkillSetRepository skillSetRepository, SkillSetMapper skillSetMapper) {
        this.skillSetRepository = skillSetRepository;
        this.skillSetMapper = skillSetMapper;
    }

    /**
     * Return a {@link Page} of {@link SkillSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillSetDTO> findByCriteria(SkillSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillSet> specification = createSpecification(criteria);
        return skillSetRepository.findAll(specification, page).map(skillSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillSet> specification = createSpecification(criteria);
        return skillSetRepository.count(specification);
    }

    /**
     * Function to convert {@link SkillSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SkillSet> createSpecification(SkillSetCriteria criteria) {
        Specification<SkillSet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            //            if (criteria.getId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getId(), SkillSet_.id));
            //            }
            //            if (criteria.getName() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getName(), SkillSet_.name));
            //            }
            //            if (criteria.getProfieciencyLevel() != null) {
            //                specification = specification.and(buildSpecification(criteria.getProfieciencyLevel(), SkillSet_.profieciencyLevel));
            //            }
            //            if (criteria.getLastAssessedDate() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getLastAssessedDate(), SkillSet_.lastAssessedDate));
            //            }
            //            if (criteria.getEmployeeId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(criteria.getEmployeeId(), root -> root.join(SkillSet_.employees, JoinType.LEFT).get(Employee_.id))
            //                );
            //            }
        }
        return specification;
    }
}
