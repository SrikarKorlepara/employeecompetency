package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.EmployeeTraining;
import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.dto.EmployeeTrainingDTO;
import com.mycompany.myapp.service.dto.TrainingProgramDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeTraining} and its DTO {@link EmployeeTrainingDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeTrainingMapper extends EntityMapper<EmployeeTrainingDTO, EmployeeTraining> {
    @Mapping(target = "trainingProgram", source = "trainingProgram", qualifiedByName = "trainingProgramId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EmployeeTrainingDTO toDto(EmployeeTraining s);

    @Named("trainingProgramId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainingProgramDTO toDtoTrainingProgramId(TrainingProgram trainingProgram);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
