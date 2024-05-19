package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.service.dto.TrainingProgramDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingProgram} and its DTO {@link TrainingProgramDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingProgramMapper extends EntityMapper<TrainingProgramDTO, TrainingProgram> {}
