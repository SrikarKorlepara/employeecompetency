package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CompetencyTestSamples.*;
import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompetencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competency.class);
        Competency competency1 = getCompetencySample1();
        Competency competency2 = new Competency();
        assertThat(competency1).isNotEqualTo(competency2);

        competency2.setId(competency1.getId());
        assertThat(competency1).isEqualTo(competency2);

        competency2 = getCompetencySample2();
        assertThat(competency1).isNotEqualTo(competency2);
    }

    @Test
    void employeeTest() throws Exception {
        Competency competency = getCompetencyRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        competency.addEmployee(employeeBack);
        assertThat(competency.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getCompetencies()).containsOnly(competency);

        competency.removeEmployee(employeeBack);
        assertThat(competency.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getCompetencies()).doesNotContain(competency);

        competency.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(competency.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getCompetencies()).containsOnly(competency);

        competency.setEmployees(new HashSet<>());
        assertThat(competency.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getCompetencies()).doesNotContain(competency);
    }
}
