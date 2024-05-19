package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PerformanceReviewAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerformanceReviewAllPropertiesEquals(PerformanceReview expected, PerformanceReview actual) {
        assertPerformanceReviewAutoGeneratedPropertiesEquals(expected, actual);
        assertPerformanceReviewAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerformanceReviewAllUpdatablePropertiesEquals(PerformanceReview expected, PerformanceReview actual) {
        assertPerformanceReviewUpdatableFieldsEquals(expected, actual);
        assertPerformanceReviewUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerformanceReviewAutoGeneratedPropertiesEquals(PerformanceReview expected, PerformanceReview actual) {
        assertThat(expected)
            .as("Verify PerformanceReview auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerformanceReviewUpdatableFieldsEquals(PerformanceReview expected, PerformanceReview actual) {
        assertThat(expected)
            .as("Verify PerformanceReview relevant properties")
            .satisfies(e -> assertThat(e.getReviewId()).as("check reviewId").isEqualTo(actual.getReviewId()))
            .satisfies(e -> assertThat(e.getReviewDate()).as("check reviewDate").isEqualTo(actual.getReviewDate()))
            .satisfies(e -> assertThat(e.getComments()).as("check comments").isEqualTo(actual.getComments()))
            .satisfies(e -> assertThat(e.getOverallRating()).as("check overallRating").isEqualTo(actual.getOverallRating()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPerformanceReviewUpdatableRelationshipsEquals(PerformanceReview expected, PerformanceReview actual) {
        assertThat(expected)
            .as("Verify PerformanceReview relationships")
            .satisfies(e -> assertThat(e.getEmployee()).as("check employee").isEqualTo(actual.getEmployee()))
            .satisfies(e -> assertThat(e.getReviewer()).as("check reviewer").isEqualTo(actual.getReviewer()));
    }
}