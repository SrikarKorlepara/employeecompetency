package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Competency;
import com.mycompany.myapp.repository.CompetencyRepository;
import com.mycompany.myapp.service.dto.CompetencyDTO;
import com.mycompany.myapp.service.mapper.CompetencyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Competency}.
 */
@Service
@Transactional
public class CompetencyService {

    private final Logger log = LoggerFactory.getLogger(CompetencyService.class);

    private final CompetencyRepository competencyRepository;

    private final CompetencyMapper competencyMapper;

    public CompetencyService(CompetencyRepository competencyRepository, CompetencyMapper competencyMapper) {
        this.competencyRepository = competencyRepository;
        this.competencyMapper = competencyMapper;
    }

    /**
     * Save a competency.
     *
     * @param competencyDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetencyDTO save(CompetencyDTO competencyDTO) {
        log.debug("Request to save Competency : {}", competencyDTO);
        Competency competency = competencyMapper.toEntity(competencyDTO);
        competency = competencyRepository.save(competency);
        return competencyMapper.toDto(competency);
    }

    /**
     * Update a competency.
     *
     * @param competencyDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetencyDTO update(CompetencyDTO competencyDTO) {
        log.debug("Request to update Competency : {}", competencyDTO);
        Competency competency = competencyMapper.toEntity(competencyDTO);
        competency = competencyRepository.save(competency);
        return competencyMapper.toDto(competency);
    }

    /**
     * Partially update a competency.
     *
     * @param competencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompetencyDTO> partialUpdate(CompetencyDTO competencyDTO) {
        log.debug("Request to partially update Competency : {}", competencyDTO);

        return competencyRepository
            .findById(competencyDTO.getId())
            .map(existingCompetency -> {
                competencyMapper.partialUpdate(existingCompetency, competencyDTO);

                return existingCompetency;
            })
            .map(competencyRepository::save)
            .map(competencyMapper::toDto);
    }

    /**
     * Get one competency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetencyDTO> findOne(Long id) {
        log.debug("Request to get Competency : {}", id);
        return competencyRepository.findById(id).map(competencyMapper::toDto);
    }

    /**
     * Delete the competency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Competency : {}", id);
        competencyRepository.deleteById(id);
    }
}
