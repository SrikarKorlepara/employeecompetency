package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.repository.TrainingProgramRepository;
import com.mycompany.myapp.service.dto.TrainingProgramDTO;
import com.mycompany.myapp.service.mapper.TrainingProgramMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.TrainingProgram}.
 */
@Service
@Transactional
public class TrainingProgramService {

    private final Logger log = LoggerFactory.getLogger(TrainingProgramService.class);

    private final TrainingProgramRepository trainingProgramRepository;

    private final TrainingProgramMapper trainingProgramMapper;

    public TrainingProgramService(TrainingProgramRepository trainingProgramRepository, TrainingProgramMapper trainingProgramMapper) {
        this.trainingProgramRepository = trainingProgramRepository;
        this.trainingProgramMapper = trainingProgramMapper;
    }

    /**
     * Save a trainingProgram.
     *
     * @param trainingProgramDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingProgramDTO save(TrainingProgramDTO trainingProgramDTO) {
        log.debug("Request to save TrainingProgram : {}", trainingProgramDTO);
        TrainingProgram trainingProgram = trainingProgramMapper.toEntity(trainingProgramDTO);
        trainingProgram = trainingProgramRepository.save(trainingProgram);
        return trainingProgramMapper.toDto(trainingProgram);
    }

    /**
     * Update a trainingProgram.
     *
     * @param trainingProgramDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingProgramDTO update(TrainingProgramDTO trainingProgramDTO) {
        log.debug("Request to update TrainingProgram : {}", trainingProgramDTO);
        TrainingProgram trainingProgram = trainingProgramMapper.toEntity(trainingProgramDTO);
        trainingProgram = trainingProgramRepository.save(trainingProgram);
        return trainingProgramMapper.toDto(trainingProgram);
    }

    /**
     * Partially update a trainingProgram.
     *
     * @param trainingProgramDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrainingProgramDTO> partialUpdate(TrainingProgramDTO trainingProgramDTO) {
        log.debug("Request to partially update TrainingProgram : {}", trainingProgramDTO);

        return trainingProgramRepository
            .findById(trainingProgramDTO.getId())
            .map(existingTrainingProgram -> {
                trainingProgramMapper.partialUpdate(existingTrainingProgram, trainingProgramDTO);

                return existingTrainingProgram;
            })
            .map(trainingProgramRepository::save)
            .map(trainingProgramMapper::toDto);
    }

    /**
     * Get one trainingProgram by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrainingProgramDTO> findOne(Long id) {
        log.debug("Request to get TrainingProgram : {}", id);
        return trainingProgramRepository.findById(id).map(trainingProgramMapper::toDto);
    }

    /**
     * Delete the trainingProgram by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TrainingProgram : {}", id);
        trainingProgramRepository.deleteById(id);
    }
}
