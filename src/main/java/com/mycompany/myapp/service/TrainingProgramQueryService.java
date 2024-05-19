package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.repository.TrainingProgramRepository;
import com.mycompany.myapp.service.criteria.TrainingProgramCriteria;
import com.mycompany.myapp.service.dto.TrainingProgramDTO;
import com.mycompany.myapp.service.mapper.TrainingProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link TrainingProgram} entities in the database.
 * The main input is a {@link TrainingProgramCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TrainingProgramDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingProgramQueryService extends QueryService<TrainingProgram> {

    private final Logger log = LoggerFactory.getLogger(TrainingProgramQueryService.class);

    private final TrainingProgramRepository trainingProgramRepository;

    private final TrainingProgramMapper trainingProgramMapper;

    public TrainingProgramQueryService(TrainingProgramRepository trainingProgramRepository, TrainingProgramMapper trainingProgramMapper) {
        this.trainingProgramRepository = trainingProgramRepository;
        this.trainingProgramMapper = trainingProgramMapper;
    }

    /**
     * Return a {@link Page} of {@link TrainingProgramDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingProgramDTO> findByCriteria(TrainingProgramCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingProgram> specification = createSpecification(criteria);
        return trainingProgramRepository.findAll(specification, page).map(trainingProgramMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingProgramCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrainingProgram> specification = createSpecification(criteria);
        return trainingProgramRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingProgramCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrainingProgram> createSpecification(TrainingProgramCriteria criteria) {
        Specification<TrainingProgram> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            //            if (criteria.getId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getId(), TrainingProgram_.id));
            //            }
            //            if (criteria.getTrainingId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getTrainingId(), TrainingProgram_.trainingId));
            //            }
            //            if (criteria.getTrainingName() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getTrainingName(), TrainingProgram_.trainingName));
            //            }
            //            if (criteria.getStartDate() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), TrainingProgram_.startDate));
            //            }
            //            if (criteria.getEndDate() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), TrainingProgram_.endDate));
            //            }
        }
        return specification;
    }
}
