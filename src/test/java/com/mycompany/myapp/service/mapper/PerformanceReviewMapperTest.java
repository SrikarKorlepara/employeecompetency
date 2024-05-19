package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.PerformanceReviewAsserts.*;
import static com.mycompany.myapp.domain.PerformanceReviewTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerformanceReviewMapperTest {

    private PerformanceReviewMapper performanceReviewMapper;

    @BeforeEach
    void setUp() {
        performanceReviewMapper = new PerformanceReviewMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPerformanceReviewSample1();
        var actual = performanceReviewMapper.toEntity(performanceReviewMapper.toDto(expected));
        assertPerformanceReviewAllPropertiesEquals(expected, actual);
    }
}
