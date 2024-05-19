package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.EmployeeTraining;
import com.mycompany.myapp.repository.EmployeeTrainingRepository;
import com.mycompany.myapp.service.criteria.EmployeeTrainingCriteria;
import com.mycompany.myapp.service.dto.EmployeeTrainingDTO;
import com.mycompany.myapp.service.mapper.EmployeeTrainingMapper;
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
 * Service for executing complex queries for {@link EmployeeTraining} entities in the database.
 * The main input is a {@link EmployeeTrainingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmployeeTrainingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeTrainingQueryService extends QueryService<EmployeeTraining> {

    private final Logger log = LoggerFactory.getLogger(EmployeeTrainingQueryService.class);

    private final EmployeeTrainingRepository employeeTrainingRepository;

    private final EmployeeTrainingMapper employeeTrainingMapper;

    public EmployeeTrainingQueryService(
        EmployeeTrainingRepository employeeTrainingRepository,
        EmployeeTrainingMapper employeeTrainingMapper
    ) {
        this.employeeTrainingRepository = employeeTrainingRepository;
        this.employeeTrainingMapper = employeeTrainingMapper;
    }

    /**
     * Return a {@link Page} of {@link EmployeeTrainingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeTrainingDTO> findByCriteria(EmployeeTrainingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeTraining> specification = createSpecification(criteria);
        return employeeTrainingRepository.findAll(specification, page).map(employeeTrainingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeTrainingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeTraining> specification = createSpecification(criteria);
        return employeeTrainingRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeTrainingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeTraining> createSpecification(EmployeeTrainingCriteria criteria) {
        Specification<EmployeeTraining> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            //            if (criteria.getId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeTraining_.id));
            //            }
            //            if (criteria.getCompletionStatus() != null) {
            //                specification = specification.and(
            //                    buildStringSpecification(criteria.getCompletionStatus(), EmployeeTraining_.completionStatus)
            //                );
            //            }
            //            if (criteria.getCompletionDate() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getCompletionDate(), EmployeeTraining_.completionDate));
            //            }
            //            if (criteria.getTrainingProgramId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getTrainingProgramId(),
            //                        root -> root.join(EmployeeTraining_.trainingProgram, JoinType.LEFT).get(TrainingProgram_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getEmployeeId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getEmployeeId(),
            //                        root -> root.join(EmployeeTraining_.employee, JoinType.LEFT).get(Employee_.id)
            //                    )
            //                );
            //            }
        }
        return specification;
    }
}
