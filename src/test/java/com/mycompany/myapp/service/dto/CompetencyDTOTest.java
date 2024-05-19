package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetencyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetencyDTO.class);
        CompetencyDTO competencyDTO1 = new CompetencyDTO();
        competencyDTO1.setId(1L);
        CompetencyDTO competencyDTO2 = new CompetencyDTO();
        assertThat(competencyDTO1).isNotEqualTo(competencyDTO2);
        competencyDTO2.setId(competencyDTO1.getId());
        assertThat(competencyDTO1).isEqualTo(competencyDTO2);
        competencyDTO2.setId(2L);
        assertThat(competencyDTO1).isNotEqualTo(competencyDTO2);
        competencyDTO1.setId(null);
        assertThat(competencyDTO1).isNotEqualTo(competencyDTO2);
    }
}
