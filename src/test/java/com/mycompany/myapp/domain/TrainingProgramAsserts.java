package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingProgramAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrainingProgramAllPropertiesEquals(TrainingProgram expected, TrainingProgram actual) {
        assertTrainingProgramAutoGeneratedPropertiesEquals(expected, actual);
        assertTrainingProgramAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrainingProgramAllUpdatablePropertiesEquals(TrainingProgram expected, TrainingProgram actual) {
        assertTrainingProgramUpdatableFieldsEquals(expected, actual);
        assertTrainingProgramUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrainingProgramAutoGeneratedPropertiesEquals(TrainingProgram expected, TrainingProgram actual) {
        assertThat(expected)
            .as("Verify TrainingProgram auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrainingProgramUpdatableFieldsEquals(TrainingProgram expected, TrainingProgram actual) {
        assertThat(expected)
            .as("Verify TrainingProgram relevant properties")
            .satisfies(e -> assertThat(e.getTrainingId()).as("check trainingId").isEqualTo(actual.getTrainingId()))
            .satisfies(e -> assertThat(e.getTrainingName()).as("check trainingName").isEqualTo(actual.getTrainingName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getStartDate()).as("check startDate").isEqualTo(actual.getStartDate()))
            .satisfies(e -> assertThat(e.getEndDate()).as("check endDate").isEqualTo(actual.getEndDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTrainingProgramUpdatableRelationshipsEquals(TrainingProgram expected, TrainingProgram actual) {}
}
