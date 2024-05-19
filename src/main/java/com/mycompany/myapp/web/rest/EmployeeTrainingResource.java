package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EmployeeTrainingRepository;
import com.mycompany.myapp.service.EmployeeTrainingQueryService;
import com.mycompany.myapp.service.EmployeeTrainingService;
import com.mycompany.myapp.service.criteria.EmployeeTrainingCriteria;
import com.mycompany.myapp.service.dto.EmployeeTrainingDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EmployeeTraining}.
 */
@RestController
@RequestMapping("/api/employee-trainings")
public class EmployeeTrainingResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTrainingResource.class);

    private static final String ENTITY_NAME = "employeeTraining";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeTrainingService employeeTrainingService;

    private final EmployeeTrainingRepository employeeTrainingRepository;

    private final EmployeeTrainingQueryService employeeTrainingQueryService;

    public EmployeeTrainingResource(
        EmployeeTrainingService employeeTrainingService,
        EmployeeTrainingRepository employeeTrainingRepository,
        EmployeeTrainingQueryService employeeTrainingQueryService
    ) {
        this.employeeTrainingService = employeeTrainingService;
        this.employeeTrainingRepository = employeeTrainingRepository;
        this.employeeTrainingQueryService = employeeTrainingQueryService;
    }

    /**
     * {@code POST  /employee-trainings} : Create a new employeeTraining.
     *
     * @param employeeTrainingDTO the employeeTrainingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeTrainingDTO, or with status {@code 400 (Bad Request)} if the employeeTraining has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmployeeTrainingDTO> createEmployeeTraining(@RequestBody EmployeeTrainingDTO employeeTrainingDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeTraining : {}", employeeTrainingDTO);
        if (employeeTrainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeTraining cannot already have an ID", ENTITY_NAME, "idexists");
        }
        employeeTrainingDTO = employeeTrainingService.save(employeeTrainingDTO);
        return ResponseEntity.created(new URI("/api/employee-trainings/" + employeeTrainingDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, employeeTrainingDTO.getId().toString()))
            .body(employeeTrainingDTO);
    }

    /**
     * {@code PUT  /employee-trainings/:id} : Updates an existing employeeTraining.
     *
     * @param id the id of the employeeTrainingDTO to save.
     * @param employeeTrainingDTO the employeeTrainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTrainingDTO,
     * or with status {@code 400 (Bad Request)} if the employeeTrainingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeTrainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeTrainingDTO> updateEmployeeTraining(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeTrainingDTO employeeTrainingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeTraining : {}, {}", id, employeeTrainingDTO);
        if (employeeTrainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeTrainingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeTrainingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        employeeTrainingDTO = employeeTrainingService.update(employeeTrainingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeTrainingDTO.getId().toString()))
            .body(employeeTrainingDTO);
    }

    /**
     * {@code PATCH  /employee-trainings/:id} : Partial updates given fields of an existing employeeTraining, field will ignore if it is null
     *
     * @param id the id of the employeeTrainingDTO to save.
     * @param employeeTrainingDTO the employeeTrainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeTrainingDTO,
     * or with status {@code 400 (Bad Request)} if the employeeTrainingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeTrainingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeTrainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeTrainingDTO> partialUpdateEmployeeTraining(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeTrainingDTO employeeTrainingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeTraining partially : {}, {}", id, employeeTrainingDTO);
        if (employeeTrainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeTrainingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeTrainingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeTrainingDTO> result = employeeTrainingService.partialUpdate(employeeTrainingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeTrainingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-trainings} : get all the employeeTrainings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeTrainings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmployeeTrainingDTO>> getAllEmployeeTrainings(
        EmployeeTrainingCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeTrainings by criteria: {}", criteria);

        Page<EmployeeTrainingDTO> page = employeeTrainingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-trainings/count} : count all the employeeTrainings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEmployeeTrainings(EmployeeTrainingCriteria criteria) {
        log.debug("REST request to count EmployeeTrainings by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeTrainingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-trainings/:id} : get the "id" employeeTraining.
     *
     * @param id the id of the employeeTrainingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeTrainingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTrainingDTO> getEmployeeTraining(@PathVariable("id") Long id) {
        log.debug("REST request to get EmployeeTraining : {}", id);
        Optional<EmployeeTrainingDTO> employeeTrainingDTO = employeeTrainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeTrainingDTO);
    }

    /**
     * {@code DELETE  /employee-trainings/:id} : delete the "id" employeeTraining.
     *
     * @param id the id of the employeeTrainingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeTraining(@PathVariable("id") Long id) {
        log.debug("REST request to delete EmployeeTraining : {}", id);
        employeeTrainingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
