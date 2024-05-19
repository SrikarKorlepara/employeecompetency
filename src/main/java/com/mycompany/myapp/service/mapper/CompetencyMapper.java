package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Competency;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.service.dto.CompetencyDTO;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competency} and its DTO {@link CompetencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetencyMapper extends EntityMapper<CompetencyDTO, Competency> {
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeEmployeeIdSet")
    CompetencyDTO toDto(Competency s);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "removeEmployee", ignore = true)
    Competency toEntity(CompetencyDTO competencyDTO);

    @Named("employeeEmployeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "employeeId", source = "employeeId")
    EmployeeDTO toDtoEmployeeEmployeeId(Employee employee);

    @Named("employeeEmployeeIdSet")
    default Set<EmployeeDTO> toDtoEmployeeEmployeeIdSet(Set<Employee> employee) {
        return employee.stream().map(this::toDtoEmployeeEmployeeId).collect(Collectors.toSet());
    }
}
