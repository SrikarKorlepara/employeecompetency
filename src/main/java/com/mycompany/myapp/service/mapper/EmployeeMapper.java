package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Competency;
import com.mycompany.myapp.domain.Department;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.SkillSet;
import com.mycompany.myapp.service.dto.CompetencyDTO;
import com.mycompany.myapp.service.dto.DepartmentDTO;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.dto.SkillSetDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "skillSets", source = "skillSets", qualifiedByName = "skillSetNameSet")
    @Mapping(target = "competencies", source = "competencies", qualifiedByName = "competencyCompetencyNameSet")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentDepartmentName")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "removeSkillSet", ignore = true)
    @Mapping(target = "removeCompetency", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("skillSetName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SkillSetDTO toDtoSkillSetName(SkillSet skillSet);

    @Named("skillSetNameSet")
    default Set<SkillSetDTO> toDtoSkillSetNameSet(Set<SkillSet> skillSet) {
        return skillSet.stream().map(this::toDtoSkillSetName).collect(Collectors.toSet());
    }

    @Named("competencyCompetencyName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "competencyName", source = "competencyName")
    CompetencyDTO toDtoCompetencyCompetencyName(Competency competency);

    @Named("competencyCompetencyNameSet")
    default Set<CompetencyDTO> toDtoCompetencyCompetencyNameSet(Set<Competency> competency) {
        return competency.stream().map(this::toDtoCompetencyCompetencyName).collect(Collectors.toSet());
    }

    @Named("departmentDepartmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "departmentName", source = "departmentName")
    DepartmentDTO toDtoDepartmentDepartmentName(Department department);
}
