package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.SkillSetAsserts.*;
import static com.mycompany.myapp.domain.SkillSetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SkillSetMapperTest {

    private SkillSetMapper skillSetMapper;

    @BeforeEach
    void setUp() {
        skillSetMapper = new SkillSetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSkillSetSample1();
        var actual = skillSetMapper.toEntity(skillSetMapper.toDto(expected));
        assertSkillSetAllPropertiesEquals(expected, actual);
    }
}
