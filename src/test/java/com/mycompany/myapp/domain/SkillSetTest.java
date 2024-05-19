package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.SkillSetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SkillSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillSet.class);
        SkillSet skillSet1 = getSkillSetSample1();
        SkillSet skillSet2 = new SkillSet();
        assertThat(skillSet1).isNotEqualTo(skillSet2);

        skillSet2.setId(skillSet1.getId());
        assertThat(skillSet1).isEqualTo(skillSet2);

        skillSet2 = getSkillSetSample2();
        assertThat(skillSet1).isNotEqualTo(skillSet2);
    }

    @Test
    void employeeTest() throws Exception {
        SkillSet skillSet = getSkillSetRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        skillSet.addEmployee(employeeBack);
        assertThat(skillSet.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getSkillSets()).containsOnly(skillSet);

        skillSet.removeEmployee(employeeBack);
        assertThat(skillSet.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getSkillSets()).doesNotContain(skillSet);

        skillSet.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(skillSet.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getSkillSets()).containsOnly(skillSet);

        skillSet.setEmployees(new HashSet<>());
        assertThat(skillSet.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getSkillSets()).doesNotContain(skillSet);
    }
}
