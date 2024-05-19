package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.EmployeeTrainingAsserts.*;
import static com.mycompany.myapp.domain.EmployeeTrainingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeTrainingMapperTest {

    private EmployeeTrainingMapper employeeTrainingMapper;

    @BeforeEach
    void setUp() {
        employeeTrainingMapper = new EmployeeTrainingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmployeeTrainingSample1();
        var actual = employeeTrainingMapper.toEntity(employeeTrainingMapper.toDto(expected));
        assertEmployeeTrainingAllPropertiesEquals(expected, actual);
    }
}
