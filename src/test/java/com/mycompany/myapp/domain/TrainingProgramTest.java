package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.TrainingProgramTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingProgram.class);
        TrainingProgram trainingProgram1 = getTrainingProgramSample1();
        TrainingProgram trainingProgram2 = new TrainingProgram();
        assertThat(trainingProgram1).isNotEqualTo(trainingProgram2);

        trainingProgram2.setId(trainingProgram1.getId());
        assertThat(trainingProgram1).isEqualTo(trainingProgram2);

        trainingProgram2 = getTrainingProgramSample2();
        assertThat(trainingProgram1).isNotEqualTo(trainingProgram2);
    }
}
