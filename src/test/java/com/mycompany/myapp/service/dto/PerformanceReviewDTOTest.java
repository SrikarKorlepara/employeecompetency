package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceReviewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceReviewDTO.class);
        PerformanceReviewDTO performanceReviewDTO1 = new PerformanceReviewDTO();
        performanceReviewDTO1.setId(1L);
        PerformanceReviewDTO performanceReviewDTO2 = new PerformanceReviewDTO();
        assertThat(performanceReviewDTO1).isNotEqualTo(performanceReviewDTO2);
        performanceReviewDTO2.setId(performanceReviewDTO1.getId());
        assertThat(performanceReviewDTO1).isEqualTo(performanceReviewDTO2);
        performanceReviewDTO2.setId(2L);
        assertThat(performanceReviewDTO1).isNotEqualTo(performanceReviewDTO2);
        performanceReviewDTO1.setId(null);
        assertThat(performanceReviewDTO1).isNotEqualTo(performanceReviewDTO2);
    }
}
