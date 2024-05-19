package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.TrainingProgramAsserts.*;
import static com.mycompany.myapp.domain.TrainingProgramTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainingProgramMapperTest {

    private TrainingProgramMapper trainingProgramMapper;

    @BeforeEach
    void setUp() {
        trainingProgramMapper = new TrainingProgramMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTrainingProgramSample1();
        var actual = trainingProgramMapper.toEntity(trainingProgramMapper.toDto(expected));
        assertTrainingProgramAllPropertiesEquals(expected, actual);
    }
}
