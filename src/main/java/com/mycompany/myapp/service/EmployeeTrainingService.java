package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EmployeeTraining;
import com.mycompany.myapp.repository.EmployeeTrainingRepository;
import com.mycompany.myapp.service.dto.EmployeeTrainingDTO;
import com.mycompany.myapp.service.mapper.EmployeeTrainingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.EmployeeTraining}.
 */
@Service
@Transactional
public class EmployeeTrainingService {

    private final Logger log = LoggerFactory.getLogger(EmployeeTrainingService.class);

    private final EmployeeTrainingRepository employeeTrainingRepository;

    private final EmployeeTrainingMapper employeeTrainingMapper;

    public EmployeeTrainingService(EmployeeTrainingRepository employeeTrainingRepository, EmployeeTrainingMapper employeeTrainingMapper) {
        this.employeeTrainingRepository = employeeTrainingRepository;
        this.employeeTrainingMapper = employeeTrainingMapper;
    }

    /**
     * Save a employeeTraining.
     *
     * @param employeeTrainingDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeTrainingDTO save(EmployeeTrainingDTO employeeTrainingDTO) {
        log.debug("Request to save EmployeeTraining : {}", employeeTrainingDTO);
        EmployeeTraining employeeTraining = employeeTrainingMapper.toEntity(employeeTrainingDTO);
        employeeTraining = employeeTrainingRepository.save(employeeTraining);
        return employeeTrainingMapper.toDto(employeeTraining);
    }

    /**
     * Update a employeeTraining.
     *
     * @param employeeTrainingDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeTrainingDTO update(EmployeeTrainingDTO employeeTrainingDTO) {
        log.debug("Request to update EmployeeTraining : {}", employeeTrainingDTO);
        EmployeeTraining employeeTraining = employeeTrainingMapper.toEntity(employeeTrainingDTO);
        employeeTraining = employeeTrainingRepository.save(employeeTraining);
        return employeeTrainingMapper.toDto(employeeTraining);
    }

    /**
     * Partially update a employeeTraining.
     *
     * @param employeeTrainingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeTrainingDTO> partialUpdate(EmployeeTrainingDTO employeeTrainingDTO) {
        log.debug("Request to partially update EmployeeTraining : {}", employeeTrainingDTO);

        return employeeTrainingRepository
            .findById(employeeTrainingDTO.getId())
            .map(existingEmployeeTraining -> {
                employeeTrainingMapper.partialUpdate(existingEmployeeTraining, employeeTrainingDTO);

                return existingEmployeeTraining;
            })
            .map(employeeTrainingRepository::save)
            .map(employeeTrainingMapper::toDto);
    }

    /**
     * Get one employeeTraining by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeTrainingDTO> findOne(Long id) {
        log.debug("Request to get EmployeeTraining : {}", id);
        return employeeTrainingRepository.findById(id).map(employeeTrainingMapper::toDto);
    }

    /**
     * Delete the employeeTraining by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeTraining : {}", id);
        employeeTrainingRepository.deleteById(id);
    }
}
