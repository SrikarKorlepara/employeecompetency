package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PerformanceReviewRepository;
import com.mycompany.myapp.service.PerformanceReviewQueryService;
import com.mycompany.myapp.service.PerformanceReviewService;
import com.mycompany.myapp.service.criteria.PerformanceReviewCriteria;
import com.mycompany.myapp.service.dto.PerformanceReviewDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PerformanceReview}.
 */
@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceReviewResource.class);

    private static final String ENTITY_NAME = "performanceReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceReviewService performanceReviewService;

    private final PerformanceReviewRepository performanceReviewRepository;

    private final PerformanceReviewQueryService performanceReviewQueryService;

    public PerformanceReviewResource(
        PerformanceReviewService performanceReviewService,
        PerformanceReviewRepository performanceReviewRepository,
        PerformanceReviewQueryService performanceReviewQueryService
    ) {
        this.performanceReviewService = performanceReviewService;
        this.performanceReviewRepository = performanceReviewRepository;
        this.performanceReviewQueryService = performanceReviewQueryService;
    }

    /**
     * {@code POST  /performance-reviews} : Create a new performanceReview.
     *
     * @param performanceReviewDTO the performanceReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceReviewDTO, or with status {@code 400 (Bad Request)} if the performanceReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerformanceReviewDTO> createPerformanceReview(@RequestBody PerformanceReviewDTO performanceReviewDTO)
        throws URISyntaxException {
        log.debug("REST request to save PerformanceReview : {}", performanceReviewDTO);
        if (performanceReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new performanceReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        performanceReviewDTO = performanceReviewService.save(performanceReviewDTO);
        return ResponseEntity.created(new URI("/api/performance-reviews/" + performanceReviewDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, performanceReviewDTO.getId().toString()))
            .body(performanceReviewDTO);
    }

    /**
     * {@code PUT  /performance-reviews/:id} : Updates an existing performanceReview.
     *
     * @param id the id of the performanceReviewDTO to save.
     * @param performanceReviewDTO the performanceReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceReviewDTO,
     * or with status {@code 400 (Bad Request)} if the performanceReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> updatePerformanceReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceReviewDTO performanceReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceReview : {}, {}", id, performanceReviewDTO);
        if (performanceReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        performanceReviewDTO = performanceReviewService.update(performanceReviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceReviewDTO.getId().toString()))
            .body(performanceReviewDTO);
    }

    /**
     * {@code PATCH  /performance-reviews/:id} : Partial updates given fields of an existing performanceReview, field will ignore if it is null
     *
     * @param id the id of the performanceReviewDTO to save.
     * @param performanceReviewDTO the performanceReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceReviewDTO,
     * or with status {@code 400 (Bad Request)} if the performanceReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the performanceReviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceReviewDTO> partialUpdatePerformanceReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceReviewDTO performanceReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceReview partially : {}, {}", id, performanceReviewDTO);
        if (performanceReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceReviewDTO> result = performanceReviewService.partialUpdate(performanceReviewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceReviewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-reviews} : get all the performanceReviews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceReviews in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerformanceReviewDTO>> getAllPerformanceReviews(
        PerformanceReviewCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceReviews by criteria: {}", criteria);

        Page<PerformanceReviewDTO> page = performanceReviewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-reviews/count} : count all the performanceReviews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerformanceReviews(PerformanceReviewCriteria criteria) {
        log.debug("REST request to count PerformanceReviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceReviewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-reviews/:id} : get the "id" performanceReview.
     *
     * @param id the id of the performanceReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> getPerformanceReview(@PathVariable("id") Long id) {
        log.debug("REST request to get PerformanceReview : {}", id);
        Optional<PerformanceReviewDTO> performanceReviewDTO = performanceReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceReviewDTO);
    }

    /**
     * {@code DELETE  /performance-reviews/:id} : delete the "id" performanceReview.
     *
     * @param id the id of the performanceReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformanceReview(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerformanceReview : {}", id);
        performanceReviewService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
