package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.EmployeeTestSamples.*;
import static com.mycompany.myapp.domain.EmployeeTrainingTestSamples.*;
import static com.mycompany.myapp.domain.TrainingProgramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTrainingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeTraining.class);
        EmployeeTraining employeeTraining1 = getEmployeeTrainingSample1();
        EmployeeTraining employeeTraining2 = new EmployeeTraining();
        assertThat(employeeTraining1).isNotEqualTo(employeeTraining2);

        employeeTraining2.setId(employeeTraining1.getId());
        assertThat(employeeTraining1).isEqualTo(employeeTraining2);

        employeeTraining2 = getEmployeeTrainingSample2();
        assertThat(employeeTraining1).isNotEqualTo(employeeTraining2);
    }

    @Test
    void trainingProgramTest() throws Exception {
        EmployeeTraining employeeTraining = getEmployeeTrainingRandomSampleGenerator();
        TrainingProgram trainingProgramBack = getTrainingProgramRandomSampleGenerator();

        employeeTraining.setTrainingProgram(trainingProgramBack);
        assertThat(employeeTraining.getTrainingProgram()).isEqualTo(trainingProgramBack);

        employeeTraining.trainingProgram(null);
        assertThat(employeeTraining.getTrainingProgram()).isNull();
    }

    @Test
    void employeeTest() throws Exception {
        EmployeeTraining employeeTraining = getEmployeeTrainingRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        employeeTraining.setEmployee(employeeBack);
        assertThat(employeeTraining.getEmployee()).isEqualTo(employeeBack);

        employeeTraining.employee(null);
        assertThat(employeeTraining.getEmployee()).isNull();
    }
}
