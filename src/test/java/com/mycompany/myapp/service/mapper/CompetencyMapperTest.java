package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CompetencyAsserts.*;
import static com.mycompany.myapp.domain.CompetencyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetencyMapperTest {

    private CompetencyMapper competencyMapper;

    @BeforeEach
    void setUp() {
        competencyMapper = new CompetencyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCompetencySample1();
        var actual = competencyMapper.toEntity(competencyMapper.toDto(expected));
        assertCompetencyAllPropertiesEquals(expected, actual);
    }
}
