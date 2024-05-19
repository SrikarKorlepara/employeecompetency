package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PerformanceReview;
import com.mycompany.myapp.repository.PerformanceReviewRepository;
import com.mycompany.myapp.service.dto.PerformanceReviewDTO;
import com.mycompany.myapp.service.mapper.PerformanceReviewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.PerformanceReview}.
 */
@Service
@Transactional
public class PerformanceReviewService {

    private final Logger log = LoggerFactory.getLogger(PerformanceReviewService.class);

    private final PerformanceReviewRepository performanceReviewRepository;

    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewService(
        PerformanceReviewRepository performanceReviewRepository,
        PerformanceReviewMapper performanceReviewMapper
    ) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    /**
     * Save a performanceReview.
     *
     * @param performanceReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceReviewDTO save(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to save PerformanceReview : {}", performanceReviewDTO);
        PerformanceReview performanceReview = performanceReviewMapper.toEntity(performanceReviewDTO);
        performanceReview = performanceReviewRepository.save(performanceReview);
        return performanceReviewMapper.toDto(performanceReview);
    }

    /**
     * Update a performanceReview.
     *
     * @param performanceReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceReviewDTO update(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to update PerformanceReview : {}", performanceReviewDTO);
        PerformanceReview performanceReview = performanceReviewMapper.toEntity(performanceReviewDTO);
        performanceReview = performanceReviewRepository.save(performanceReview);
        return performanceReviewMapper.toDto(performanceReview);
    }

    /**
     * Partially update a performanceReview.
     *
     * @param performanceReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceReviewDTO> partialUpdate(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to partially update PerformanceReview : {}", performanceReviewDTO);

        return performanceReviewRepository
            .findById(performanceReviewDTO.getId())
            .map(existingPerformanceReview -> {
                performanceReviewMapper.partialUpdate(existingPerformanceReview, performanceReviewDTO);

                return existingPerformanceReview;
            })
            .map(performanceReviewRepository::save)
            .map(performanceReviewMapper::toDto);
    }

    /**
     * Get one performanceReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceReviewDTO> findOne(Long id) {
        log.debug("Request to get PerformanceReview : {}", id);
        return performanceReviewRepository.findById(id).map(performanceReviewMapper::toDto);
    }

    /**
     * Delete the performanceReview by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceReview : {}", id);
        performanceReviewRepository.deleteById(id);
    }
}
