package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SkillSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillSetDTO.class);
        SkillSetDTO skillSetDTO1 = new SkillSetDTO();
        skillSetDTO1.setId(1L);
        SkillSetDTO skillSetDTO2 = new SkillSetDTO();
        assertThat(skillSetDTO1).isNotEqualTo(skillSetDTO2);
        skillSetDTO2.setId(skillSetDTO1.getId());
        assertThat(skillSetDTO1).isEqualTo(skillSetDTO2);
        skillSetDTO2.setId(2L);
        assertThat(skillSetDTO1).isNotEqualTo(skillSetDTO2);
        skillSetDTO1.setId(null);
        assertThat(skillSetDTO1).isNotEqualTo(skillSetDTO2);
    }
}
