package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SkillSet;
import com.mycompany.myapp.repository.SkillSetRepository;
import com.mycompany.myapp.service.dto.SkillSetDTO;
import com.mycompany.myapp.service.mapper.SkillSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.SkillSet}.
 */
@Service
@Transactional
public class SkillSetService {

    private final Logger log = LoggerFactory.getLogger(SkillSetService.class);

    private final SkillSetRepository skillSetRepository;

    private final SkillSetMapper skillSetMapper;

    public SkillSetService(SkillSetRepository skillSetRepository, SkillSetMapper skillSetMapper) {
        this.skillSetRepository = skillSetRepository;
        this.skillSetMapper = skillSetMapper;
    }

    /**
     * Save a skillSet.
     *
     * @param skillSetDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillSetDTO save(SkillSetDTO skillSetDTO) {
        log.debug("Request to save SkillSet : {}", skillSetDTO);
        SkillSet skillSet = skillSetMapper.toEntity(skillSetDTO);
        skillSet = skillSetRepository.save(skillSet);
        return skillSetMapper.toDto(skillSet);
    }

    /**
     * Update a skillSet.
     *
     * @param skillSetDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillSetDTO update(SkillSetDTO skillSetDTO) {
        log.debug("Request to update SkillSet : {}", skillSetDTO);
        SkillSet skillSet = skillSetMapper.toEntity(skillSetDTO);
        skillSet = skillSetRepository.save(skillSet);
        return skillSetMapper.toDto(skillSet);
    }

    /**
     * Partially update a skillSet.
     *
     * @param skillSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SkillSetDTO> partialUpdate(SkillSetDTO skillSetDTO) {
        log.debug("Request to partially update SkillSet : {}", skillSetDTO);

        return skillSetRepository
            .findById(skillSetDTO.getId())
            .map(existingSkillSet -> {
                skillSetMapper.partialUpdate(existingSkillSet, skillSetDTO);

                return existingSkillSet;
            })
            .map(skillSetRepository::save)
            .map(skillSetMapper::toDto);
    }

    /**
     * Get one skillSet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SkillSetDTO> findOne(Long id) {
        log.debug("Request to get SkillSet : {}", id);
        return skillSetRepository.findById(id).map(skillSetMapper::toDto);
    }

    /**
     * Delete the skillSet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SkillSet : {}", id);
        skillSetRepository.deleteById(id);
    }
}
