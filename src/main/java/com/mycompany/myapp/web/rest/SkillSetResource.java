package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SkillSetRepository;
import com.mycompany.myapp.service.SkillSetQueryService;
import com.mycompany.myapp.service.SkillSetService;
import com.mycompany.myapp.service.criteria.SkillSetCriteria;
import com.mycompany.myapp.service.dto.SkillSetDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SkillSet}.
 */
@RestController
@RequestMapping("/api/skill-sets")
public class SkillSetResource {

    private final Logger log = LoggerFactory.getLogger(SkillSetResource.class);

    private static final String ENTITY_NAME = "skillSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillSetService skillSetService;

    private final SkillSetRepository skillSetRepository;

    private final SkillSetQueryService skillSetQueryService;

    public SkillSetResource(
        SkillSetService skillSetService,
        SkillSetRepository skillSetRepository,
        SkillSetQueryService skillSetQueryService
    ) {
        this.skillSetService = skillSetService;
        this.skillSetRepository = skillSetRepository;
        this.skillSetQueryService = skillSetQueryService;
    }

    /**
     * {@code POST  /skill-sets} : Create a new skillSet.
     *
     * @param skillSetDTO the skillSetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillSetDTO, or with status {@code 400 (Bad Request)} if the skillSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SkillSetDTO> createSkillSet(@RequestBody SkillSetDTO skillSetDTO) throws URISyntaxException {
        log.debug("REST request to save SkillSet : {}", skillSetDTO);
        if (skillSetDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        skillSetDTO = skillSetService.save(skillSetDTO);
        return ResponseEntity.created(new URI("/api/skill-sets/" + skillSetDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, skillSetDTO.getId().toString()))
            .body(skillSetDTO);
    }

    /**
     * {@code PUT  /skill-sets/:id} : Updates an existing skillSet.
     *
     * @param id the id of the skillSetDTO to save.
     * @param skillSetDTO the skillSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillSetDTO,
     * or with status {@code 400 (Bad Request)} if the skillSetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SkillSetDTO> updateSkillSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SkillSetDTO skillSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SkillSet : {}, {}", id, skillSetDTO);
        if (skillSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        skillSetDTO = skillSetService.update(skillSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillSetDTO.getId().toString()))
            .body(skillSetDTO);
    }

    /**
     * {@code PATCH  /skill-sets/:id} : Partial updates given fields of an existing skillSet, field will ignore if it is null
     *
     * @param id the id of the skillSetDTO to save.
     * @param skillSetDTO the skillSetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillSetDTO,
     * or with status {@code 400 (Bad Request)} if the skillSetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the skillSetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the skillSetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SkillSetDTO> partialUpdateSkillSet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SkillSetDTO skillSetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SkillSet partially : {}, {}", id, skillSetDTO);
        if (skillSetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillSetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillSetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SkillSetDTO> result = skillSetService.partialUpdate(skillSetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillSetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /skill-sets} : get all the skillSets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillSets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SkillSetDTO>> getAllSkillSets(
        SkillSetCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SkillSets by criteria: {}", criteria);

        Page<SkillSetDTO> page = skillSetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skill-sets/count} : count all the skillSets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSkillSets(SkillSetCriteria criteria) {
        log.debug("REST request to count SkillSets by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillSetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-sets/:id} : get the "id" skillSet.
     *
     * @param id the id of the skillSetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillSetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SkillSetDTO> getSkillSet(@PathVariable("id") Long id) {
        log.debug("REST request to get SkillSet : {}", id);
        Optional<SkillSetDTO> skillSetDTO = skillSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillSetDTO);
    }

    /**
     * {@code DELETE  /skill-sets/:id} : delete the "id" skillSet.
     *
     * @param id the id of the skillSetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkillSet(@PathVariable("id") Long id) {
        log.debug("REST request to delete SkillSet : {}", id);
        skillSetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
