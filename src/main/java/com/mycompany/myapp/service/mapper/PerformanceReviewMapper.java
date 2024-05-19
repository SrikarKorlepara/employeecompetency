package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.PerformanceReview;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.dto.PerformanceReviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerformanceReview} and its DTO {@link PerformanceReviewDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerformanceReviewMapper extends EntityMapper<PerformanceReviewDTO, PerformanceReview> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "reviewer", source = "reviewer", qualifiedByName = "employeeId")
    PerformanceReviewDTO toDto(PerformanceReview s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
