package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTrainingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeTrainingDTO.class);
        EmployeeTrainingDTO employeeTrainingDTO1 = new EmployeeTrainingDTO();
        employeeTrainingDTO1.setId(1L);
        EmployeeTrainingDTO employeeTrainingDTO2 = new EmployeeTrainingDTO();
        assertThat(employeeTrainingDTO1).isNotEqualTo(employeeTrainingDTO2);
        employeeTrainingDTO2.setId(employeeTrainingDTO1.getId());
        assertThat(employeeTrainingDTO1).isEqualTo(employeeTrainingDTO2);
        employeeTrainingDTO2.setId(2L);
        assertThat(employeeTrainingDTO1).isNotEqualTo(employeeTrainingDTO2);
        employeeTrainingDTO1.setId(null);
        assertThat(employeeTrainingDTO1).isNotEqualTo(employeeTrainingDTO2);
    }
}
