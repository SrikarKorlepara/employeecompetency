package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.service.criteria.EmployeeCriteria;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.mapper.EmployeeMapper;
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
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeQueryService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.fetchBagRelationships(employeeRepository.findAll(specification, page)).map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            //            if (criteria.getId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getId(), Employee_.id));
            //            }
            //            if (criteria.getEmployeeId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), Employee_.employeeId));
            //            }
            //            if (criteria.getFirstName() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Employee_.firstName));
            //            }
            //            if (criteria.getLastName() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getLastName(), Employee_.lastName));
            //            }
            //            if (criteria.getEmail() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employee_.email));
            //            }
            //            if (criteria.getPhone() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getPhone(), Employee_.phone));
            //            }
            //            if (criteria.getPosition() != null) {
            //                specification = specification.and(buildSpecification(criteria.getPosition(), Employee_.position));
            //            }
            //            if (criteria.getDateOfJoining() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getDateOfJoining(), Employee_.dateOfJoining));
            //            }
            //            if (criteria.getStatus() != null) {
            //                specification = specification.and(buildSpecification(criteria.getStatus(), Employee_.status));
            //            }
            //            if (criteria.getEmployeeTrainingId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getEmployeeTrainingId(),
            //                        root -> root.join(Employee_.employeeTrainings, JoinType.LEFT).get(EmployeeTraining_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getPerformanceReviewId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getPerformanceReviewId(),
            //                        root -> root.join(Employee_.performanceReviews, JoinType.LEFT).get(PerformanceReview_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getReviewerId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getReviewerId(),
            //                        root -> root.join(Employee_.reviewers, JoinType.LEFT).get(PerformanceReview_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getSkillSetId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(criteria.getSkillSetId(), root -> root.join(Employee_.skillSets, JoinType.LEFT).get(SkillSet_.id))
            //                );
            //            }
            //            if (criteria.getCompetencyId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getCompetencyId(),
            //                        root -> root.join(Employee_.competencies, JoinType.LEFT).get(Competency_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getDepartmentId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getDepartmentId(),
            //                        root -> root.join(Employee_.department, JoinType.LEFT).get(Department_.id)
            //                    )
            //                );
            //            }
        }
        return specification;
    }
}
