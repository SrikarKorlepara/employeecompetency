package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.PerformanceReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceReview.class);
        PerformanceReview performanceReview1 = getPerformanceReviewSample1();
        PerformanceReview performanceReview2 = new PerformanceReview();
        assertThat(performanceReview1).isNotEqualTo(performanceReview2);

        performanceReview2.setId(performanceReview1.getId());
        assertThat(performanceReview1).isEqualTo(performanceReview2);

        performanceReview2 = getPerformanceReviewSample2();
        assertThat(performanceReview1).isNotEqualTo(performanceReview2);
    }

    @Test
    void employeeTest() throws Exception {
        PerformanceReview performanceReview = getPerformanceReviewRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        performanceReview.setEmployee(employeeBack);
        assertThat(performanceReview.getEmployee()).isEqualTo(employeeBack);

        performanceReview.employee(null);
        assertThat(performanceReview.getEmployee()).isNull();
    }

    @Test
    void reviewerTest() throws Exception {
        PerformanceReview performanceReview = getPerformanceReviewRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        performanceReview.setReviewer(employeeBack);
        assertThat(performanceReview.getReviewer()).isEqualTo(employeeBack);

        performanceReview.reviewer(null);
        assertThat(performanceReview.getReviewer()).isNull();
    }
}
