package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.SkillSet;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.dto.SkillSetDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkillSet} and its DTO {@link SkillSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface SkillSetMapper extends EntityMapper<SkillSetDTO, SkillSet> {
    @Mapping(target = "employees", source = "employees", qualifiedByName = "employeeEmployeeIdSet")
    SkillSetDTO toDto(SkillSet s);

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "removeEmployee", ignore = true)
    SkillSet toEntity(SkillSetDTO skillSetDTO);

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
