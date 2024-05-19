package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PerformanceReview;
import com.mycompany.myapp.repository.PerformanceReviewRepository;
import com.mycompany.myapp.service.criteria.PerformanceReviewCriteria;
import com.mycompany.myapp.service.dto.PerformanceReviewDTO;
import com.mycompany.myapp.service.mapper.PerformanceReviewMapper;
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
 * Service for executing complex queries for {@link PerformanceReview} entities in the database.
 * The main input is a {@link PerformanceReviewCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerformanceReviewDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceReviewQueryService extends QueryService<PerformanceReview> {

    private final Logger log = LoggerFactory.getLogger(PerformanceReviewQueryService.class);

    private final PerformanceReviewRepository performanceReviewRepository;

    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewQueryService(
        PerformanceReviewRepository performanceReviewRepository,
        PerformanceReviewMapper performanceReviewMapper
    ) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    /**
     * Return a {@link Page} of {@link PerformanceReviewDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceReviewDTO> findByCriteria(PerformanceReviewCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerformanceReview> specification = createSpecification(criteria);
        return performanceReviewRepository.findAll(specification, page).map(performanceReviewMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerformanceReviewCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerformanceReview> specification = createSpecification(criteria);
        return performanceReviewRepository.count(specification);
    }

    /**
     * Function to convert {@link PerformanceReviewCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerformanceReview> createSpecification(PerformanceReviewCriteria criteria) {
        Specification<PerformanceReview> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            //            if (criteria.getId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getId(), PerformanceReview_.id));
            //            }
            //            if (criteria.getReviewId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getReviewId(), PerformanceReview_.reviewId));
            //            }
            //            if (criteria.getReviewDate() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getReviewDate(), PerformanceReview_.reviewDate));
            //            }
            //            if (criteria.getOverallRating() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getOverallRating(), PerformanceReview_.overallRating));
            //            }
            //            if (criteria.getEmployeeId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getEmployeeId(),
            //                        root -> root.join(PerformanceReview_.employee, JoinType.LEFT).get(Employee_.id)
            //                    )
            //                );
            //            }
            //            if (criteria.getReviewerId() != null) {
            //                specification = specification.and(
            //                    buildSpecification(
            //                        criteria.getReviewerId(),
            //                        root -> root.join(PerformanceReview_.reviewer, JoinType.LEFT).get(Employee_.id)
            //                    )
            //                );
            //            }
        }
        return specification;
    }
}
