package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CompetencyTestSamples.*;
import static com.mycompany.myapp.domain.DepartmentTestSamples.*;
import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.EmployeeTrainingTestSamples.*;
import static com.mycompany.myapp.domain.PerformanceReviewTestSamples.*;
import static com.mycompany.myapp.domain.SkillSetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void employeeTrainingTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        EmployeeTraining employeeTrainingBack = getEmployeeTrainingRandomSampleGenerator();

        employee.addEmployeeTraining(employeeTrainingBack);
        assertThat(employee.getEmployeeTrainings()).containsOnly(employeeTrainingBack);
        assertThat(employeeTrainingBack.getEmployee()).isEqualTo(employee);

        employee.removeEmployeeTraining(employeeTrainingBack);
        assertThat(employee.getEmployeeTrainings()).doesNotContain(employeeTrainingBack);
        assertThat(employeeTrainingBack.getEmployee()).isNull();

        employee.employeeTrainings(new HashSet<>(Set.of(employeeTrainingBack)));
        assertThat(employee.getEmployeeTrainings()).containsOnly(employeeTrainingBack);
        assertThat(employeeTrainingBack.getEmployee()).isEqualTo(employee);

        employee.setEmployeeTrainings(new HashSet<>());
        assertThat(employee.getEmployeeTrainings()).doesNotContain(employeeTrainingBack);
        assertThat(employeeTrainingBack.getEmployee()).isNull();
    }

    @Test
    void performanceReviewTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PerformanceReview performanceReviewBack = getPerformanceReviewRandomSampleGenerator();

        employee.addPerformanceReview(performanceReviewBack);
        assertThat(employee.getPerformanceReviews()).containsOnly(performanceReviewBack);
        assertThat(performanceReviewBack.getEmployee()).isEqualTo(employee);

        employee.removePerformanceReview(performanceReviewBack);
        assertThat(employee.getPerformanceReviews()).doesNotContain(performanceReviewBack);
        assertThat(performanceReviewBack.getEmployee()).isNull();

        employee.performanceReviews(new HashSet<>(Set.of(performanceReviewBack)));
        assertThat(employee.getPerformanceReviews()).containsOnly(performanceReviewBack);
        assertThat(performanceReviewBack.getEmployee()).isEqualTo(employee);

        employee.setPerformanceReviews(new HashSet<>());
        assertThat(employee.getPerformanceReviews()).doesNotContain(performanceReviewBack);
        assertThat(performanceReviewBack.getEmployee()).isNull();
    }

    @Test
    void reviewerTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        PerformanceReview performanceReviewBack = getPerformanceReviewRandomSampleGenerator();

        employee.addReviewer(performanceReviewBack);
        assertThat(employee.getReviewers()).containsOnly(performanceReviewBack);
        assertThat(performanceReviewBack.getReviewer()).isEqualTo(employee);

        employee.removeReviewer(performanceReviewBack);
        assertThat(employee.getReviewers()).doesNotContain(performanceReviewBack);
        assertThat(performanceReviewBack.getReviewer()).isNull();

        employee.reviewers(new HashSet<>(Set.of(performanceReviewBack)));
        assertThat(employee.getReviewers()).containsOnly(performanceReviewBack);
        assertThat(performanceReviewBack.getReviewer()).isEqualTo(employee);

        employee.setReviewers(new HashSet<>());
        assertThat(employee.getReviewers()).doesNotContain(performanceReviewBack);
        assertThat(performanceReviewBack.getReviewer()).isNull();
    }

    @Test
    void skillSetTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        SkillSet skillSetBack = getSkillSetRandomSampleGenerator();

        employee.addSkillSet(skillSetBack);
        assertThat(employee.getSkillSets()).containsOnly(skillSetBack);

        employee.removeSkillSet(skillSetBack);
        assertThat(employee.getSkillSets()).doesNotContain(skillSetBack);

        employee.skillSets(new HashSet<>(Set.of(skillSetBack)));
        assertThat(employee.getSkillSets()).containsOnly(skillSetBack);

        employee.setSkillSets(new HashSet<>());
        assertThat(employee.getSkillSets()).doesNotContain(skillSetBack);
    }

    @Test
    void competencyTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Competency competencyBack = getCompetencyRandomSampleGenerator();

        employee.addCompetency(competencyBack);
        assertThat(employee.getCompetencies()).containsOnly(competencyBack);

        employee.removeCompetency(competencyBack);
        assertThat(employee.getCompetencies()).doesNotContain(competencyBack);

        employee.competencies(new HashSet<>(Set.of(competencyBack)));
        assertThat(employee.getCompetencies()).containsOnly(competencyBack);

        employee.setCompetencies(new HashSet<>());
        assertThat(employee.getCompetencies()).doesNotContain(competencyBack);
    }

    @Test
    void departmentTest() throws Exception {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.setDepartment(departmentBack);
        assertThat(employee.getDepartment()).isEqualTo(departmentBack);

        employee.department(null);
        assertThat(employee.getDepartment()).isNull();
    }
}
